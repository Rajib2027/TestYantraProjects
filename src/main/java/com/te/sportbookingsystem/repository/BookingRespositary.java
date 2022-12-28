package com.te.sportbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.te.sportbookingsystem.entity.Booking;

@Repository
public interface BookingRespositary extends JpaRepository<Booking, Integer> {

//	Object findAvailableSessions(Integer sportId, LocalDate date);

}
