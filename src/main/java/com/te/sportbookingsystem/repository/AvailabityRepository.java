package com.te.sportbookingsystem.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.sportbookingsystem.entity.AvailabiltyCheckingDate;
@Repository
public interface AvailabityRepository extends JpaRepository<AvailabiltyCheckingDate, Integer>{

	Optional<AvailabiltyCheckingDate> findByBookedDate(LocalDate bookDate);
}
