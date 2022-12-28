package com.te.sportbookingsystem.service.impl;

import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.te.sportbookingsystem.dto.AdminfetchUserDto;
import com.te.sportbookingsystem.dto.BookStatusDto;
import com.te.sportbookingsystem.dto.DetailsGroundDto;
import com.te.sportbookingsystem.dto.GroundDeleteDto;
import com.te.sportbookingsystem.entity.BookStatus;
import com.te.sportbookingsystem.entity.DefaultUser;
import com.te.sportbookingsystem.entity.DetailsGround;
import com.te.sportbookingsystem.exception.InvalidException;
import com.te.sportbookingsystem.repository.BookStatusRespository;
import com.te.sportbookingsystem.repository.DefaultUserResposotary;
import com.te.sportbookingsystem.repository.DetailsRepository;
import com.te.sportbookingsystem.service.AdminService;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	private BookStatusRespository bookStatusRespository;
	@Autowired
	private DefaultUserResposotary defaultUserResposotary;
	@Autowired
	private DetailsRepository detailsRepository;

	@Autowired
	private DetailsGround ground;

	@Override
	public BookStatus setStatusbyAdmin(BookStatusDto statusDto) {

		Optional<BookStatus> findById = bookStatusRespository.findById(statusDto.getBookStatusId());
		if (findById.isPresent()) {
			BookStatus bookStatus = findById.get();
			bookStatus.setName(statusDto.getName());
			return bookStatusRespository.save(bookStatus);
		} else {
			throw new InvalidException("Invalid statusId");
		}
	}

	@Override
	public AdminfetchUserDto getUserID(Integer userId) {

		Optional<DefaultUser> id = defaultUserResposotary.findById(userId);
		if (id.isPresent()) {
			DefaultUser defaultUser = id.get();
			AdminfetchUserDto adminfetchUserDto = new AdminfetchUserDto();
			BeanUtils.copyProperties(defaultUser, adminfetchUserDto);
			return adminfetchUserDto;
		} else {
			throw new InvalidException("something went wrong");
		}
	}

	@Override
	public DetailsGround createGround(DetailsGroundDto detailsGround) {
		BeanUtils.copyProperties(detailsGround, ground);

		return detailsRepository.save(ground);
	}

	@Override
	public DetailsGround updateGround(DetailsGroundDto detailsGround) {
		Optional<DetailsGround> optional = detailsRepository.findById(detailsGround.getGroundName());
		if (optional.isPresent()) {
			DetailsGround inputGound = optional.get();
			BeanUtils.copyProperties(detailsGround, inputGound);
			return detailsRepository.save(inputGound);
		} else {
			throw new InvalidException("The Field Is Not Available");
		}
	}

	@Override
	public boolean deleteGround(GroundDeleteDto deleteDto) {
		Optional<DetailsGround> optional = detailsRepository.findById(deleteDto.getGroundName());
		if (optional.isPresent()) {
			DetailsGround detailsGround = optional.get();
			detailsRepository.delete(detailsGround);
			return true;

		} else {
			throw new InvalidException("The Ground Name is Invalid");
		}
	}

}
