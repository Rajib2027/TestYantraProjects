package com.te.sportbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.sportbookingsystem.entity.DetailsGround;
@Repository
public interface DetailsRepository extends JpaRepository<DetailsGround,String>{

}
