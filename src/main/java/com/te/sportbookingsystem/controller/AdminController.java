package com.te.sportbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.te.sportbookingsystem.dto.AdminfetchUserDto;
import com.te.sportbookingsystem.dto.BookStatusDto;
import com.te.sportbookingsystem.dto.DetailsGroundDto;
import com.te.sportbookingsystem.dto.GroundDeleteDto;
import com.te.sportbookingsystem.entity.BookStatus;
import com.te.sportbookingsystem.entity.DetailsGround;
import com.te.sportbookingsystem.exception.InvalidException;
import com.te.sportbookingsystem.response.Response;
import com.te.sportbookingsystem.service.AdminService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/booking/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private Response response;

	@PostMapping("/status")
	public ResponseEntity<Response> statusUpdate(@RequestBody BookStatusDto statusDto) {
		BookStatus bookStatus = adminService.setStatusbyAdmin(statusDto);
		if (bookStatus != null) {
			response.setStatus("false");
			response.setError(200);
			response.setMessage("Admin setting the status");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

	@GetMapping("/DetailsbyUserId{userId}")
	public ResponseEntity<Response> getdetailsbyUserID(@PathVariable Integer userId) {
		AdminfetchUserDto dto = adminService.getUserID(userId);
		if (dto != null) {
			response.setData(dto);
			response.setStatus("false");
			response.setError(200);
			response.setMessage("Information of User ID");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

	@PutMapping("/CreateGround")
	public ResponseEntity<Response> createGround(@RequestBody DetailsGroundDto detailsGround) {
		DetailsGround details = adminService.createGround(detailsGround);
		if (details != null) {
			response.setStatus("false");
			response.setError(200);
			response.setMessage("Data Added Successfully");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

	@PutMapping("/updateGround")
	public ResponseEntity<Response> updateGround(@RequestBody DetailsGroundDto detailsGround) {
		DetailsGround details = adminService.updateGround(detailsGround);
		if (details != null) {
			response.setStatus("false");
			response.setError(200);
			response.setMessage("Update Successfully");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {

			throw new InvalidException("Something went wrong");
		}
	}

	@DeleteMapping("/deleteGround")
	public ResponseEntity<Response> deleteByAdmin(@RequestBody GroundDeleteDto deleteDto) {
		boolean defaultUser = adminService.deleteGround(deleteDto);
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
