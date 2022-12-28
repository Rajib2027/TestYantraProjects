package com.te.sportbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.sportbookingsystem.entity.BookStatus;

@Repository
public interface BookStatusRespository extends JpaRepository<BookStatus,Integer>{

}
