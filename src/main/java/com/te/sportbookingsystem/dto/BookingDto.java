package com.te.sportbookingsystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
	private LocalDate startDate;
	private LocalDate endDATE;
	private String sportName;
	private String description;
	private LocalTime startHour;
	private LocalTime endingHour;
	private LocalDateTime requestOn;

}
