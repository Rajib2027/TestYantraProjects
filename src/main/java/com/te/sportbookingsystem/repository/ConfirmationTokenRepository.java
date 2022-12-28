package com.te.sportbookingsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.te.sportbookingsystem.entity.ConfirmationToken;

@Repository

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
	//@Query(nativeQuery = true)
  public abstract  ConfirmationToken findByConfirmationToken(String confirmationToken);
}

