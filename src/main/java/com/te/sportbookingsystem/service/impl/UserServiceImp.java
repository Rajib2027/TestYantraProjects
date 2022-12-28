package com.te.sportbookingsystem.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.te.sportbookingsystem.dto.BookStatusDto;
import com.te.sportbookingsystem.dto.BookedSessionDto;
import com.te.sportbookingsystem.dto.BookingDto;
import com.te.sportbookingsystem.dto.DefaultUserDto;
import com.te.sportbookingsystem.dto.ResetPassword;
import com.te.sportbookingsystem.dto.SportFieldDeleteDto;
import com.te.sportbookingsystem.dto.SportFieldDto;
import com.te.sportbookingsystem.entity.AppUser;
import com.te.sportbookingsystem.entity.AvailabiltyCheckingDate;
import com.te.sportbookingsystem.entity.BookStatus;
import com.te.sportbookingsystem.entity.Booking;
import com.te.sportbookingsystem.entity.ConfirmationToken;
import com.te.sportbookingsystem.entity.DefaultUser;
import com.te.sportbookingsystem.entity.DetailsGround;
import com.te.sportbookingsystem.entity.Roles;
import com.te.sportbookingsystem.entity.SportField;
import com.te.sportbookingsystem.entity.User;
import com.te.sportbookingsystem.exception.InvalidException;
import com.te.sportbookingsystem.exception.InvalidUserCredentialException;
import com.te.sportbookingsystem.repository.AppUserRepository;
import com.te.sportbookingsystem.repository.AvailabityRepository;
import com.te.sportbookingsystem.repository.BookStatusRespository;
import com.te.sportbookingsystem.repository.BookingRespositary;
import com.te.sportbookingsystem.repository.ConfirmationTokenRepository;
import com.te.sportbookingsystem.repository.DefaultUserResposotary;
import com.te.sportbookingsystem.repository.DetailsRepository;
import com.te.sportbookingsystem.repository.RoleRepository;
import com.te.sportbookingsystem.repository.SportFieldRespository;
import com.te.sportbookingsystem.repository.UserRepository;
import com.te.sportbookingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;

@Log4j2
@Service
@RequiredArgsConstructor
@Component
public class UserServiceImp implements UserService {

	int diffHour;
	double totalPricePerHour;
	int startHour;
	int endHour;
	double totalAmount;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private BookingRespositary bookingRespositary;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DefaultUserResposotary defaultUserResposotary;

	@Autowired
	private SportFieldRespository fieldRespositary;

	@Autowired
	private BookStatusRespository bookStatusRespository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private DefaultUser defaultUser;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Booking booking;

	@Autowired
	private SportField field;
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private BookStatus bookStatus;
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private DetailsRepository detailsRepository;
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private AvailabityRepository availabityRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public BCryptPasswordEncoder encoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public JavaMailSender javaMailSender() {
		return new JavaMailSenderImpl();
	}

	UUID uuid = UUID.randomUUID();
	String uuidAsString = uuid.toString();

	@Override
	public boolean register(DefaultUserDto userDto) {
		DefaultUser defaultUser = new DefaultUser();
		BeanUtils.copyProperties(userDto, defaultUser);
		Optional<Roles> optional = roleRepository.findByRoleName("USER");
		if (optional.isPresent()) {
			Roles role = optional.get();
			User newUser = User.builder().userName(defaultUser.getUserName()).role(role)
					.password((new BCryptPasswordEncoder().encode(defaultUser.getPassword()))).build();
			userRepository.save(newUser);
			defaultUserResposotary.save(defaultUser);
			return true;
		}
		return false;
	}

	@Override
	public Optional<String> userRegistration(DefaultUserDto dto) {
		log.info("userServiceImpl:registration logic for registration service started, {}", dto);
		if (dto.getUserId() != null) {
//		user.setPassword(encoder.encode(user.getPassword()));
//			UserResponse findByEmail = findByEmail(dto.getEmail());
//			if (findByEmail == null) {
			DefaultUser user = new DefaultUser();
			user.setPassword(uuidAsString);
			BeanUtils.copyProperties(dto, user);
			log.debug("userServiceImpl:registeruser, user entity object created {}", user);
//				user.setPassword(otp()+user.getUserName());
//				userRepo.save(user);
			Optional<Roles> userRole = roleRepository.findByRoleName("ROLE_USER");
			if (userRole.isPresent()) {
				log.debug("userServiceImpl:registerUser, role found in database");
				Roles roles = userRole.get();
				AppUser appUser = AppUser.builder().userName(user.getUserId()).roles(Lists.newArrayList()).build();
				ConfirmationToken confirmationToken = new ConfirmationToken(defaultUser);
				// confirmationTokenRepository.save(confirmationToken);
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(defaultUser.getEmail());
				mailMessage.setSubject("Complete Registration!");
				mailMessage.setText("To confirm your account, please click here : "
						+ "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
				sendEmail(mailMessage);
				roles.getAppUser().add(appUser);
				appUser.getRoles().add(roles);
				appUserRepository.save(appUser);
				log.info("userServiceImpl:registerUser, registraction done");
			}
			log.info("userServiceImpl:registerUser returning the data");
			return Optional.ofNullable(defaultUserResposotary.save(user).getUserId());
			// return UserResponse.builder().message("User Registration Success").build();
//			} else {
//				throw new DuplicateEmailException("This Email is already Registered");
//			}
		} else

		{
			throw new InvalidException("Invalid Input!! Please provide Correct Data");
		}
	}

	@Override
	public DefaultUser updateUser(DefaultUserDto dto) {
		DefaultUser findByEmail = defaultUserResposotary.findByEmail(dto.getEmail());
		if (findByEmail != null) {
			DefaultUser defaultUser = new DefaultUser();
			BeanUtils.copyProperties(findByEmail, defaultUser);
			return defaultUserResposotary.save(defaultUser);
		} else {
			throw new InvalidException("The userId is Not Available");
		}
	}

	@Override
	public boolean getAuthicationToken(String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		if (token != null) {
			DefaultUser user = defaultUserResposotary.findByEmail(token.getUserEntity().getEmail());
			//user.setActive(1);
			defaultUserResposotary.save(user);
			return true;
		} else {
			throw new InvalidException("The link is invalid or broken!");
		}

	}

	@Async
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}

	public void generateOneTimePassword(DefaultUser user) throws UnsupportedEncodingException, MessagingException {
		log.info("generate one-time password");
		String OTP = RandomString.make(8);
		String encodedOTP = passwordEncoder.encode(OTP);

		user.setOneTimePassword(encodedOTP);
		user.setOtpRequestedTime(new Date());

		defaultUserResposotary.save(user);

		sendOTPEmail(user, OTP);
	}

	private void sendOTPEmail(DefaultUser user, String oTP) throws UnsupportedEncodingException, MessagingException {
		log.info("sending an email that contains the OTP to the user");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@tyss.com", "Tyss Support");
		helper.setTo(user.getEmail());

		String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

		String content = "<p>Hello " + user.getUserName() + "</p>"
				+ "<p>For security reason, you're required to use the following " + "One Time Password to login:</p>"
				+ "<p><b>" + oTP + "</b></p>" + "<br>" + "<p>Note: this OTP is set to expire in 5 minutes.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

	public void clearOTP(DefaultUser user) {
		log.info("clear OTP request status upon successful login of the user.");
		user.setOneTimePassword(null);
		user.setOtpRequestedTime(null);
		defaultUserResposotary.save(user);
	}

	public String getPassword(DefaultUser user) {
		if (user.isOTPRequired()) {
			return user.getOneTimePassword();
		}

		return user.getPassword();
	}

	@Override
	public void updateResetPasswordToken(String password, String email) throws InvalidUserCredentialException {
		DefaultUser user = defaultUserResposotary.findByEmail(email);
		if (user != null) {
			user.setResetPassword(password);
			defaultUserResposotary.save(user);
		} else {
			throw new InvalidUserCredentialException("Could not find any customer with the email " + email);
		}
	}

	public DefaultUser getByResetPassword(String password) {
		return defaultUserResposotary.findByResetPassword(password);
	}

	public void updatePassword(DefaultUser user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);

		user.setResetPassword(null);
		defaultUserResposotary.save(user);
	}

	public List<BookedSessionDto> bookedSessions() {
		List<SportField> list = fieldRespositary.findAll();
		List<BookedSessionDto> bookedSessionsDtos = new ArrayList<BookedSessionDto>();
//		list.stream()
//				.forEach((i) -> bookedSessionsDtos.add(new BookedSessionDto(i.getSportfieldId(), i.getSportName(),
//						i.getDescription(), i.getStartHour(), i.getEndingHour(), i.getRequestOn(), i.getPricePerHour(),
//						i.getStartDate(), i.getEndDate())));

		return bookedSessionsDtos;
	}

	@Override
	public List<Object> findAvailableSessions(Integer sportId, LocalDate date) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Booking> criteriaQuery = cb.createQuery(Booking.class);
			Root<Booking> sportSessionRoot = criteriaQuery.from(Booking.class);
			SportField sport = new SportField();
			sport.setSportfieldId(sportId);
			LocalDateTime from = date.atStartOfDay();
			LocalDateTime until = date.atTime(LocalTime.MAX);
			criteriaQuery.select(sportSessionRoot).where(cb.equal(sportSessionRoot.get("sport"), sport),
					cb.between(sportSessionRoot.get("Time"), from, until));
//			session.createQuery(criteriaQuery).getResultList();
//			sportSessionRepository.findAvailableSessions(sportId, date)
			List<Object> list = new ArrayList<>();
			list.add(session.createQuery(criteriaQuery).getResultList());
			// list.add(bookingRespositary.findAvailableSessions(sportId, date));
			return list;
		} catch (Exception e) {
			throw new InvalidException("Error retrieving all sport sessions.");
		}
	}

	@Override
	public DefaultUser update(DefaultUserDto userDto) {
		DefaultUser existingUser = defaultUserResposotary.findByEmail(userDto.getEmail());
		BeanUtils.copyProperties(userDto, existingUser);
		return defaultUserResposotary.save(existingUser);
	}

	@Override
	public Optional<String> get(final String email) {
		return defaultUserResposotary.findByemail(email);
	}

	@Override
	public DefaultUser resetPassword(ResetPassword resetPassword) {
		DefaultUser user = defaultUserResposotary.findByEmail(resetPassword.getEmail());
		if (user != null && (resetPassword.getOldPass().equals(user.getPassword()))) {
			user.setPassword(resetPassword.getConfirmPass());

			return defaultUserResposotary.save(user);
		}
		throw new InvalidException("check your password");
	}

	@Override
	public BookStatus showStatus(BookStatusDto bookingStatusDto) {
		BookStatus bookingStatus = bookStatusRespository.findById(bookingStatusDto.getBookStatusId()).orElse(null);
		if (bookingStatus != null) {
			BeanUtils.copyProperties(bookingStatusDto, bookingStatus);
			return bookStatusRespository.save(bookingStatus);
		}
		throw new InvalidUserCredentialException("Check your filled details");
	}

	@Override
	public Booking sportFieldRegister(Integer userId, BookingDto bookingDto) {

		Optional<DefaultUser> optionalUser = defaultUserResposotary.findById(userId);
		List<Booking> listBooking = bookingRespositary.findAll();
		List<SportField> listSport = fieldRespositary.findAll();

		Stream<LocalDate> mapStartDate = listBooking.stream().filter(a -> a.getStartDate() == bookingDto.getStartDate())
				.map(m -> m.getStartDate());
		Stream<LocalDate> mapEndDate = listBooking.stream().filter(a -> a.getEndDATE() == bookingDto.getEndDATE())
				.map(m -> m.getEndDATE());
		Stream<LocalTime> mapStartHour = listSport.stream().filter(a -> a.getStartHour() == bookingDto.getStartHour())
				.map(m -> m.getStartHour());
		Stream<LocalTime> mapEndHour = listSport.stream().filter(a -> a.getEndingHour() == bookingDto.getEndingHour())
				.map(m -> m.getEndingHour());
		if (optionalUser.isPresent()) {
			DefaultUser defaultUser = optionalUser.get();

			LocalTime time = bookingDto.getStartHour();
			startHour = time.getHour();
			LocalTime localTime = bookingDto.getEndingHour();
			endHour = localTime.getHour();

			DetailsGround byId = detailsRepository.getById(bookingDto.getSportName());

			if (bookingDto.getSportName().equalsIgnoreCase(byId.getGroundName())) {
				diffHour = endHour - startHour;
				totalPricePerHour = diffHour * byId.getPriceperhour();
			}

			LocalDate dateFrom = bookingDto.getStartDate();
			LocalDate dateTo = bookingDto.getEndDATE();
			Long intervalDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
			totalAmount = totalPricePerHour * intervalDays;

			BeanUtils.copyProperties(bookingDto, booking);
			BeanUtils.copyProperties(bookingDto, field);
			BeanUtils.copyProperties(bookingDto, bookStatus);

			for (long i = 0; i <= intervalDays; i++) {
				AvailabiltyCheckingDate checkingDate = new AvailabiltyCheckingDate();
				checkingDate.setBookedDate(dateFrom.plusDays(i));
				availabityRepository.save(checkingDate);

			}
			field.setPricePerHour(totalPricePerHour);
			booking.setTotalAmount(totalAmount);
			bookingRespositary.save(booking);

			return booking;
		} else {
			throw new InvalidException("something went wrong");
		}
	}

	@Override
	public Booking updateSportField(SportFieldDto sportFieldDto) {

		Optional<SportField> findById = fieldRespositary.findById(sportFieldDto.getSportFieldId());
		Optional<Booking> byId = bookingRespositary.findById(sportFieldDto.getSportFieldId());
		if (findById.isPresent() && byId.isPresent()) {
			SportField field = findById.get();
			Booking booking = byId.get();
			BeanUtils.copyProperties(sportFieldDto, booking);
			BeanUtils.copyProperties(sportFieldDto, field);
			return bookingRespositary.save(booking);
		} else {
			throw new InvalidException("The SportFieldId is Not Available");
		}
	}

	@Override
	public boolean deleteUser(SportFieldDeleteDto deleteDto) {
		Optional<SportField> findById = fieldRespositary.findById(deleteDto.getSportfieldId());
		Optional<Booking> byId = bookingRespositary.findById(deleteDto.getSportfieldId());
		if (findById.isPresent() && byId.isPresent()) {
			SportField field = findById.get();
			Booking booking = byId.get();
			bookingRespositary.delete(booking);
			return true;
		} else {
			throw new InvalidException("The sportfieldId is Not Available");
		}
	}

}
