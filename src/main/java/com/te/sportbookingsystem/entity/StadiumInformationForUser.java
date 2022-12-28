package com.te.sportbookingsystem.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.introspect.AnnotationCollector.OneAnnotation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class StadiumInformationForUser {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@OneToMany(cascade = CascadeType.ALL,targetEntity = DetailsGround.class)
	@JoinColumn(name = "sportname")
	private List<DetailsGround> details;
	
	private String groundOpenTime;
	
	private String groundCloseTime;
	
	private Integer totalSportfield;
	
	public StadiumInformationForUser(List<DetailsGround> details, String groundOpenTime, String groundCloseTime,
			Integer totalSportfield) {
		super();
		this.details = details;
		this.groundOpenTime = groundOpenTime;
		this.groundCloseTime = groundCloseTime;
		this.totalSportfield = totalSportfield;
	}
}
