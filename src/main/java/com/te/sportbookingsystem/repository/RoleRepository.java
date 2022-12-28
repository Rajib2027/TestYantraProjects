package com.te.sportbookingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.sportbookingsystem.entity.Roles;



@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
	Optional<Roles> findByRoleId(Integer id);

	Optional<Roles> findByRoleName(String string);
}
