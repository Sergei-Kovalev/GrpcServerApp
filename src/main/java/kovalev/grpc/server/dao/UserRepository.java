package kovalev.grpc.server.dao;

import kovalev.grpc.server.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);

    @Override
    <S extends User> S save(S user);

    @Override
    void delete(User user);
}
