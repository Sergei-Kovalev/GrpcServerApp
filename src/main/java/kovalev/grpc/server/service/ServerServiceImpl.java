package kovalev.grpc.server.service;

import com.sample.GrpcService;
import com.sample.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import kovalev.grpc.server.dao.UserRepository;
import kovalev.grpc.server.entity.User;
import kovalev.grpc.server.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServerServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    UserRepository repository;

    @Override
    public void findById(GrpcService.FindByIDRequest request, StreamObserver<GrpcService.FindByIDResponse> responseObserver) {
        System.out.println("Client requested id: " + request);

        Optional<User> userOptional = repository.findById(request.getId());

        GrpcService.FindByIDResponse.Builder builder = GrpcService.FindByIDResponse.newBuilder();
        if (userOptional.isEmpty()) {
            GrpcService.ErrorFindByID errorFindByID = GrpcService.ErrorFindByID
                    .newBuilder()
                    .setErrorDescription("User with id = " + request.getId() + " not found at DB")
                    .build();

            builder.setErrorResponse(errorFindByID);
        } else {
            User user = userOptional.orElseThrow(() -> UserNotFoundException.of(User.class, request.getId()));
            System.out.println(user);

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

        GrpcService.FindByIDResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
