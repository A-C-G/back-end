package com.project.ACG.repository;

import com.project.ACG.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

	boolean existsUserByUserIdAndUserEmail(String userId, String userEmail);

	Optional<User> findUserByUserIdAndUserEmail(String userId, String userEmail);

	Optional<List<User>> findAllByStatusIsTrue();
}