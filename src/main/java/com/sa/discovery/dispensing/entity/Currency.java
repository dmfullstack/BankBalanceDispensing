package com.sa.discovery.dispensing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "currency")
@Getter
@Setter
@NoArgsConstructor
public class Currency implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "currency_code")
    private String currency_code;

    @Column(name = "decimal_places")
    private int decimalPlaces;

    @Column(name = "description")
    private String description;
}

