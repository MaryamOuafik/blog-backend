package bloggy.repositories;

import bloggy.dtos.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;


import bloggy.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String username);

}

