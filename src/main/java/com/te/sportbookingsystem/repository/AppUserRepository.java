package com.te.sportbookingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.sportbookingsystem.entity.AppUser;
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String>{

	Optional<AppUser> findByUserName(String username);
}
