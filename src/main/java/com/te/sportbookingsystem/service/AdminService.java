package com.te.sportbookingsystem.service;

import com.te.sportbookingsystem.dto.AdminfetchUserDto;
import com.te.sportbookingsystem.dto.BookStatusDto;
import com.te.sportbookingsystem.dto.DetailsGroundDto;
import com.te.sportbookingsystem.dto.GroundDeleteDto;
import com.te.sportbookingsystem.entity.BookStatus;
import com.te.sportbookingsystem.entity.DetailsGround;

public interface AdminService {

	BookStatus setStatusbyAdmin(BookStatusDto statusDto);
	AdminfetchUserDto getUserID(Integer userId);

	DetailsGround createGround(DetailsGroundDto detailsGround);

	DetailsGround updateGround(DetailsGroundDto detailsGround);

	boolean deleteGround(GroundDeleteDto deleteDto);
	
	

}
