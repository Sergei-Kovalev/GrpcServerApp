package kovalev.grpc.server.service;

import com.sample.GrpcService;
import io.grpc.stub.StreamObserver;

public interface ServerService {
    void findById(GrpcService.FindByIDRequest request, StreamObserver<GrpcService.FindByIDResponse> responseObserver);
}
