package com.te.sportbookingsystem.dto;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUserDto {

	private String userId;
	private String email;
	private String password;
	private LocalDate createdOn;
	private String userName;
	private long phoneNumber;
	private String gender;
	
	
}
