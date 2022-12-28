package com.te.sportbookingsystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.te.sportbookingsystem.entity.BookStatus;
import com.te.sportbookingsystem.entity.Booking;
import com.te.sportbookingsystem.entity.DefaultUser;
import com.te.sportbookingsystem.entity.SportField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowStatusRequestDto {

	private String sportName;
	
	@JsonFormat(pattern = "HH", shape = JsonFormat.Shape.STRING)
	private LocalTime startHour;
	
	@JsonFormat(pattern = "HH", shape = JsonFormat.Shape.STRING)
	private LocalTime endingHour;
	
	private Integer bookingId;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDATE;
	
	private Integer bookstatusId;
	
	private Integer userId;
}
