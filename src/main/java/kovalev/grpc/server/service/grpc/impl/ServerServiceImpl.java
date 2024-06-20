package kovalev.grpc.server.service.grpc.impl;

import com.sample.GrpcService;
import com.sample.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import kovalev.grpc.server.dao.UserRepository;
import kovalev.grpc.server.entity.User;
import kovalev.grpc.server.exception.UserNotFoundException;
import kovalev.grpc.server.service.grpc.ServerService;
import kovalev.grpc.server.service.kafka.KafkaProducerService;
import kovalev.grpc.server.service.kafka.event.EventForKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ServerServiceImpl extends UserServiceGrpc.UserServiceImplBase implements ServerService {

    @Autowired
    UserRepository repository;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Override
    public void findById(GrpcService.FindByIDRequest request, StreamObserver<GrpcService.FindByIDResponse> responseObserver) {
        System.out.println("Client requested id: " + request);

        Optional<User> userOptional = repository.findById(request.getId());

        GrpcService.FindByIDResponse.Builder builder = GrpcService.FindByIDResponse.newBuilder();

        selectionOfTypeAndRequestFilling(request, userOptional, builder);

        GrpcService.FindByIDResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private void selectionOfTypeAndRequestFilling(GrpcService.FindByIDRequest request, Optional<User> userOptional, GrpcService.FindByIDResponse.Builder builder) {
        if (userOptional.isEmpty()) {

            GrpcService.ErrorFindByID errorFindByID = constructErrorResponse(request);

            builder.setErrorResponse(errorFindByID);
        } else {
            User user = userOptional.orElseThrow(() -> UserNotFoundException.of(User.class, request.getId()));
            System.out.println(user);

            sendMessageToKafkaBroker(user);

            GrpcService.SuccessFindByID successFindByID = constructSuccessfullyResponse(user);

            builder.setSuccessResponse(successFindByID);
        }
    }

    private void sendMessageToKafkaBroker(User user) {
        kafkaProducerService.sendMessageAboutRequestToKafka(EventForKafka.builder()
                .userID(user.getId())
                .requestTime(LocalDateTime.now())
                .build());
    }

    private static GrpcService.SuccessFindByID constructSuccessfullyResponse(User user) {
        return GrpcService.SuccessFindByID
                .newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setSex(user.getSex())
                .setAge(user.getAge())
                .setPassportSeries(user.getPassportSeries())
                .setPassportNumber(user.getPassportNumber())
                .build();
    }

    private static GrpcService.ErrorFindByID constructErrorResponse(GrpcService.FindByIDRequest request) {
        return GrpcService.ErrorFindByID
                .newBuilder()
                .setErrorDescription("User with id = " + request.getId() + " not found at DB")
                .build();
    }
}
