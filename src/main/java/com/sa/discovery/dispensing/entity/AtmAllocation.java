package com.sa.discovery.dispensing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author dineshmetkari
 *
 */
@Entity
@Table(name = "atm_allocation")
@Getter
@Setter
@NoArgsConstructor
public class AtmAllocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NonNull
	@Column(name = "atm_allocation_id")
	private int id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "atm_id", referencedColumnName = "atm_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private ATM atm_id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "denomination_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Denomination denomination;

	@NonNull
	@Column(name = "count")
	private int count;
}
