package kovalev.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import kovalev.grpc.server.service.ServerServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class GrpcServerApp {

	public static void main(String[] args) throws IOException, InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(GrpcServerApp.class, args);

		Server server = ServerBuilder.forPort(8080)
				.addService(context.getBean(ServerServiceImpl.class))
				.build();

		server.start();

		System.out.println("Server UP!!!");

		server.awaitTermination();
	}

}
