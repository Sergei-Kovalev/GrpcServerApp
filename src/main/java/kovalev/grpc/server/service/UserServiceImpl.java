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
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    UserRepository repository;

    @Override
    public void findById(GrpcService.FindByIDRequest request, StreamObserver<GrpcService.FindByIDResponse> responseObserver) {
        System.out.println("Client requested id: " + request);

        Optional<User> userOptional = repository.findById(request.getId());

        User user = userOptional.orElseThrow(() -> UserNotFoundException.of(User.class, request.getId()));

        System.out.println(user);


        GrpcService.FindByIDResponse response = GrpcService.FindByIDResponse
                .newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setSex(user.getSex())
                .setAge(user.getAge())
                .setPassportSeries(user.getPassportSeries())
                .setPassportNumber(user.getPassportNumber())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
