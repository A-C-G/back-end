package com.project.ACG.repository;

import com.project.ACG.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

  boolean existsUserByUserIdAndUserName(String userId, String userName);
}