package com.te.sportbookingsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
	private String userId;
	private String email;
	private String oldPass;
	private String newPass;
	private String confirmPass;
}
