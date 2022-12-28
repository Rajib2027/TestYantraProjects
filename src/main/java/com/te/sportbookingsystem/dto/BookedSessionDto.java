package com.te.sportbookingsystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class BookedSessionDto {
	private Integer sportfieldId;
	private String sportName;
	private String description;
	private LocalTime startHour;
	private LocalTime endingHour;
	private LocalDateTime requestOn;
	private double pricePerHour;
	private LocalDate startDate;
	private LocalDate endDate;
}
