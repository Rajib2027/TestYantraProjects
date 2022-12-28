package com.te.sportbookingsystem.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookingId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDATE;

	private double totalAmount;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "Booking_DefaultUser", joinColumns = { @JoinColumn(name = "bookingId") }, inverseJoinColumns = {
			@JoinColumn(name = "userId") })
	private DefaultUser defaultUser;

	@OneToOne(cascade = CascadeType.ALL)
	private BookStatus bookStatus;

	@OneToMany(cascade = CascadeType.ALL)
	private List<SportField>fields;
	
	public Booking(Integer bookingId, LocalDate startDate, LocalDate endDATE) {
		super();
		this.bookingId = bookingId;
		this.startDate = startDate;
		this.endDATE = endDATE;	
	}
}