package com.te.sportbookingsystem.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Response {

	private String message;
	private int error;
	private String status;
	private Object data;
}
