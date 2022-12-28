package com.te.sportbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.sportbookingsystem.entity.SportField;
@Repository
public interface SportFieldRespository extends JpaRepository<SportField, Integer>{

}
