package com.sa.discovery.dispensing.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "denomination")
@Getter
@Setter
@NoArgsConstructor
public class Denomination implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @NonNull 
    @Column(name = "DENOMINATION_ID")    
    private int id;

    @Column(name = "value")
    private BigDecimal value;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "denomination_type_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DenominationTypeCode denominationTypeCode;
}
