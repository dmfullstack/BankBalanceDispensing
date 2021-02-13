package com.sa.discovery.dispensing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sa.discovery.dispensing.entity.Client;

import java.util.Optional;

/**
 * 
 * @author dineshmetkari
 *
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
