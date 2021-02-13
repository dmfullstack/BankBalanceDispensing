package com.sa.discovery.dispensing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "denomination_type")
@Getter
@Setter
@NoArgsConstructor
public class DenominationTypeCode implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @NonNull
    @Column(name = "denomination_type_code")
    private String denominationTypeCode;

    @Column(name = "description")
    private String description;
}
