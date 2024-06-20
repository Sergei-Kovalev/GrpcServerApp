package kovalev.grpc.server.service.impl;

import com.sample.GrpcService;
import com.sample.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import kovalev.grpc.server.controller.KafkaProducerController;
import kovalev.grpc.server.dao.UserRepository;
import kovalev.grpc.server.dto.KafkaMessageDTO;
import kovalev.grpc.server.entity.User;
import kovalev.grpc.server.exception.UserNotFoundException;
import kovalev.grpc.server.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ServerServiceImpl extends UserServiceGrpc.UserServiceImplBase implements ServerService {

    @Autowired
    UserRepository repository;

    @Autowired
    KafkaProducerController kafkaProducerController;

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
            GrpcService.ErrorFindByID errorFindByID = GrpcService.ErrorFindByID
                    .newBuilder()
                    .setErrorDescription("User with id = " + request.getId() + " not found at DB")
                    .build();

            builder.setErrorResponse(errorFindByID);
        } else {
            User user = userOptional.orElseThrow(() -> UserNotFoundException.of(User.class, request.getId()));
            System.out.println(user);

            kafkaProducerController.sendUserToKafkaBroker(KafkaMessageDTO.builder()
                    .userID(user.getId())
                    .requestTime(LocalDateTime.now())
                    .build());

            GrpcService.SuccessFindByID successFindByID = GrpcService.SuccessFindByID
                    .newBuilder()
                    .setId(user.getId())
                    .setName(user.getName())
                    .setSurname(user.getSurname())
                    .setSex(user.getSex())
                    .setAge(user.getAge())
                    .setPassportSeries(user.getPassportSeries())
                    .setPassportNumber(user.getPassportNumber())
                    .build();

            builder.setSuccessResponse(successFindByID);
        }
    }
}
