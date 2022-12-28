package com.te.sportbookingsystem.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Component
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SportFieldDto {
    private Integer sportFieldId;
	private String sportName;
	private String description;
	private LocalTime startHour;
	private LocalTime endingHour;
	private LocalDateTime requestOn;

	
	
}
