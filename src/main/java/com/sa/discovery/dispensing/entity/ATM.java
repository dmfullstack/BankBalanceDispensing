package com.sa.discovery.dispensing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The ATM class. 
 * 
 * @author dineshmetkari
 *
 */
@Entity
@Table(name = "atm")
@Getter
@Setter
@NoArgsConstructor
public class ATM implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @NonNull
    @Column(name = "atm_id")
    private int atm_id;


    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "location")
    private String location;
}
