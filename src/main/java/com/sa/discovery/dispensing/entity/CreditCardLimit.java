package com.sa.discovery.dispensing.entity;

import java.io.Serializable;

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
@Table(name = "credit_card_limit")
@Getter
@Setter
@NoArgsConstructor
public class CreditCardLimit implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_account_number", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @NonNull
    private ClientAccount clientAccountNumber;

    @NonNull
    @Column(name = "account_limit")
    private double accountLimit;
}
