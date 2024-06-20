package kovalev.grpc.server.dao;

import kovalev.grpc.server.entity.User;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    @NonNull
    Optional<User> findById(@NonNull Long id);
}
