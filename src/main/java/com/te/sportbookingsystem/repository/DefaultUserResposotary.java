package com.te.sportbookingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.te.sportbookingsystem.entity.DefaultUser;
@Repository

public interface DefaultUserResposotary extends JpaRepository<DefaultUser,Integer>{

//	@Query(nativeQuery = true)
	public  DefaultUser findByEmail(String email);
	public  DefaultUser findByUserName(String userName);
	public DefaultUser findByResetPassword(String password);
	public Optional<String> findByemail(String email);
	
}
