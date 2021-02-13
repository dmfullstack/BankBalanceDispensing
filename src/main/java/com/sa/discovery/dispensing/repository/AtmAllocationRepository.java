package com.sa.discovery.dispensing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sa.discovery.dispensing.entity.AtmAllocation;

import java.util.List;

/**
 * The AtmAllocationRepository repository class. 
 * 
 * @author dineshmetkari
 *
 */
public interface AtmAllocationRepository extends JpaRepository<AtmAllocation, Integer> {

	@Query(value = "SELECT aa.*, de.value,(aa.count*de.value) as demominationSum" + " FROM atm_allocation aa "
			+ "   join denomination de on aa.denomination_id = de.denomination_id " + " where aa.atm_id = :atmId "
			+ " order by de.value desc", nativeQuery = true)
	List<Object[]> getListOfDenominationsAndSumByAtmId(@Param("atmId") Integer atmId);

}
