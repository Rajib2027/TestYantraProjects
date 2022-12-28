package com.te.sportbookingsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.te.sportbookingsystem.dto.BookStatusDto;
import com.te.sportbookingsystem.dto.BookingDto;
import com.te.sportbookingsystem.dto.DefaultUserDto;
import com.te.sportbookingsystem.dto.ResetPassword;
import com.te.sportbookingsystem.dto.SportFieldDeleteDto;
import com.te.sportbookingsystem.dto.SportFieldDto;
import com.te.sportbookingsystem.entity.BookStatus;
import com.te.sportbookingsystem.entity.Booking;
import com.te.sportbookingsystem.entity.DefaultUser;

public interface UserService {

	//DefaultUser register(DefaultUserDto defaultUserDto);

	Booking sportFieldRegister(Integer userId, BookingDto booking);

	DefaultUser updateUser(DefaultUserDto dto);

	//boolean deleteUser(UserDeleteDto deleteDto);

	Booking updateSportField(SportFieldDto sportFieldDto);

	boolean deleteUser(SportFieldDeleteDto deleteDto);

	boolean getAuthicationToken(String confirmationToken);

	Optional<String> userRegistration(DefaultUserDto dto);

	void updateResetPasswordToken(String password, String email);

	DefaultUser update(DefaultUserDto userDto);

	 Optional<String> get(String email);

	DefaultUser resetPassword(ResetPassword resetPassword);

	BookStatus showStatus(BookStatusDto bookingStatusDto);

	List<Object> findAvailableSessions(Integer sportId, LocalDate date);

	boolean register(DefaultUserDto userDto);
	
//	String sendMail(DefaultUser user);
	

}
