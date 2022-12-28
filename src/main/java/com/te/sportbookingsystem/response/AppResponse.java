package com.te.sportbookingsystem.response;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AppResponse<T> {
	private Boolean error;
	private int Status;
	private String message;
	private T data;
	private String token;
	public AppResponse(String message, String token, T data) {
		super();
		this.message = message;
		this.token = token;
		this.data = data;
	}
}
