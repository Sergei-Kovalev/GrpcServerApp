package kovalev.grpc.server;

import kovalev.grpc.server.controller.ServerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class GrpcServerApp {

	public static void main(String[] args) throws IOException, InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(GrpcServerApp.class, args);

		context.getBean(ServerController.class).startServer();
	}

}
