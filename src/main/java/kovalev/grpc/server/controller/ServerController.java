package kovalev.grpc.server.controller;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import kovalev.grpc.server.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;


@Controller
public class ServerController {
    @Autowired
    private ServerService service;

    public void startServer() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8090)
                .addService((BindableService) service)
                .build();

        server.start();

        System.out.println("Server UP!!!");

        server.awaitTermination();
    }

}
