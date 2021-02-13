package com.sa.discovery.dispensing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sa.discovery.dispensing.entity.ATM;
import com.sa.discovery.dispensing.entity.AtmAllocation;

import java.util.List;

/**
 * The ATMRepository interface.
 * 
 * @author dineshmetkari
 *
 */
public interface ATMRepository extends JpaRepository<ATM, Integer> {

}
