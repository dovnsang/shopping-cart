package com.sangvndo.shoppingcart.repository;

import com.sangvndo.shoppingcart.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
