package com.svngdo.shoppingcart.repository;

import com.svngdo.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from users u where u.email = :email", nativeQuery = true)
    User findByEmail(String email);
}
