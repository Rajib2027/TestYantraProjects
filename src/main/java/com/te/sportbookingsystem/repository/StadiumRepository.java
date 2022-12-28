package com.te.sportbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.te.sportbookingsystem.entity.StadiumInformationForUser;
@Repository
public interface StadiumRepository extends JpaRepository<StadiumInformationForUser,Integer> {

}
