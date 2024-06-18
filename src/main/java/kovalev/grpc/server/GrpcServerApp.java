package kovalev.grpc.server;

import kovalev.grpc.server.dao.UserRepository;
import kovalev.grpc.server.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class GrpcServerApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(GrpcServerApp.class, args);

		UserRepository repository = context.getBean(UserRepository.class);

		Optional<User> byId = repository.findById(1L);

		User user = byId.get();

		System.out.println(user);
	}

}
