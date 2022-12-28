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
public class AdminfetchUserDto {

	private String email;
	private LocalDateTime createdOn;
	private String userName;
	private long phoneNumber;
	private String gender;
	private Integer userId;
	

}
