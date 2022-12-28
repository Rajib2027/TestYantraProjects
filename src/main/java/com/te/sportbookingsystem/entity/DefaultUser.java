package com.te.sportbookingsystem.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUser {

	@Id
	private String userId;
	private String email;
	private String password;
	private String userName;
	private long phoneNumber;
	private String gender;
	private String roles;
	private String resetPassword;

	private static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes

	@Column(name = "one_time_password")
	private String oneTimePassword;

	@Column(name = "otp_requested_time")
	private Date otpRequestedTime;

	public boolean isOTPRequired() {
		log.info("checking whether the OTP expires or not.");
		if (this.getOneTimePassword() == null) {
			return false;
		}
		log.info("declare a constant for the OTP expiration time in milliseconds");
		long currentTimeInMillis = System.currentTimeMillis();
		long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();

		if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
			// OTP expires
			return false;
		}

		return true;
	}

}
