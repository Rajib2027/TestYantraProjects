package com.te.sportbookingsystem.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.te.sportbookingsystem.dto.BookStatusDto;
import com.te.sportbookingsystem.dto.BookingDto;
import com.te.sportbookingsystem.dto.DefaultUserDto;
import com.te.sportbookingsystem.dto.ResetPassword;
import com.te.sportbookingsystem.dto.SportFieldDeleteDto;
import com.te.sportbookingsystem.dto.SportFieldDto;
import com.te.sportbookingsystem.entity.BookStatus;
import com.te.sportbookingsystem.entity.Booking;
import com.te.sportbookingsystem.entity.DefaultUser;
import com.te.sportbookingsystem.entity.DetailsGround;
import com.te.sportbookingsystem.exception.InvalidException;
import com.te.sportbookingsystem.exception.InvalidUserCredentialException;
import com.te.sportbookingsystem.response.AppResponse;
import com.te.sportbookingsystem.response.Response;
import com.te.sportbookingsystem.securityconfig.Utility;
import com.te.sportbookingsystem.service.impl.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth/user")
public class UserController {

	@Autowired
	private AppResponse appResponse;
	@Autowired
	private UserServiceImp service;

	@Autowired
	private Response response;
	@Autowired
	private JavaMailSender mailSender;

	@PostMapping("/registration")
	public AppResponse<String> register(@RequestBody DefaultUserDto dto) {
		log.info("userController:registerUser execution start, {}", dto);
		Optional<String> userId = service.userRegistration(dto);
		if (userId.isPresent()) {
			return new AppResponse<String>("User registration successfull!", null, userId.get());
		}
		throw new InvalidException("User registration failed");
	}

	@PostMapping("/register")
	public ResponseEntity<AppResponse> registerUser(@RequestBody DefaultUserDto defaultUserDto) {
		if (service.register(defaultUserDto)) {
			appResponse.setError(false);
			appResponse.setMessage("Successful");
			appResponse.setStatus(200);
		}
		return new ResponseEntity<AppResponse>(appResponse, HttpStatus.ACCEPTED);
	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		log.info("userController:forgotPassword request sent");
		String email = request.getParameter("email");
		String token = RandomString.make(30);

		try {
			log.debug("userController:Successfuly sent the passsword to mail");
			service.updateResetPasswordToken(token, email);
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

		} catch (InvalidUserCredentialException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email");
		}

		return "forgot_password_form";
	}

	public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@shopme.com", "Shopme Support");
		helper.setTo(recipientEmail);

		String subject = "Here's the link to reset your password";

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

	@GetMapping("/userDetaisls")
	public ResponseEntity<?> userDetails(@RequestParam String email) {
		if (email != null) {
			log.info("userController:Getting the User Details By UserID");
			Optional<String> user = service.get(email);
			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody DefaultUserDto userDto) {
		if (userDto != null) {
			log.info("userController:updating the userDetails");
			DefaultUser user = service.update(userDto);
			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PostMapping("/resetPass")
	public ResponseEntity<?> resetPass(@RequestBody ResetPassword resetPassword) {
		DefaultUser user = service.resetPassword(resetPassword);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@GetMapping("/status")
	public ResponseEntity<?> showStatus(@RequestBody BookStatusDto statusDto) {
		if (statusDto != null) {
			BookStatus status = service.showStatus(statusDto);
			if (status != null) {
				return new ResponseEntity<>(status, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<Response> getAuthicationRegister(@RequestParam("token") String confirmationToken) {

		boolean output = service.getAuthicationToken(confirmationToken);
		if (output) {
			response.setMessage("accountVerified");
			response.setStatus("false");
			response.setError(200);
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

	@RequestMapping(value = "/sport/{userId}", method = RequestMethod.POST)
	public ResponseEntity<Response> getSportFieldRegister(@PathVariable Integer userId,
			@RequestBody BookingDto booking) {
		Booking sportBooking = service.sportFieldRegister(userId, booking);
		if (sportBooking != null) {
			response.setMessage("Register Sucessfully");
			response.setStatus("false");
			response.setError(200);
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

//
//	@RequestMapping(value = "/ground/{groundName}", method = RequestMethod.GET)
//	public ResponseEntity<Response> getByGroundName(@PathVariable String groundName) {
//		if (details != null) {
//			response.setData(details);
//			response.setStatus("false");
//			response.setError(200);
//			response.setMessage("Ground detail");
//			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
//		} else {
//
//			throw new InvalidException("Something went wrong");
//		}
//	}

	@PutMapping("/updateUser")
	public ResponseEntity<Response> updateByUser(@RequestBody DefaultUserDto dto) {

		DefaultUser defaultUser = service.updateUser(dto);
		if (defaultUser != null) {
			response.setData(defaultUser);
			response.setStatus("false");
			response.setError(200);
			response.setMessage("update sucessfully");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

	@PutMapping("/updateSportField")
	public ResponseEntity<Response> updateBySportField(@RequestBody SportFieldDto sportFieldDto) {

		Booking booking = service.updateSportField(sportFieldDto);
		if (booking != null) {
			response.setStatus("false");
			response.setError(200);
			response.setMessage("update sucessfully");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

	@DeleteMapping("/deletesportfield")
	public ResponseEntity<Response> deleteByUser(@RequestBody SportFieldDeleteDto deleteDto) {
		boolean defaultUser = service.deleteUser(deleteDto);
		if (defaultUser) {
			response.setStatus("false");
			response.setError(200);
			response.setMessage("delete successfully");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

}
