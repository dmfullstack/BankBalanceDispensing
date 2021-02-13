package com.sa.discovery.dispensing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import javax.persistence.*;
import java.io.Serializable;

/**
 * The AccountType entity class. 
 * 
 * @author dineshmetkari
 *
 */
@Entity
@Table(name = "account_type")
@Getter
@Setter
@NoArgsConstructor
public class AccountType implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @NonNull
    @Column(name = "account_type_code")
    private String account_type_code;

    @NonNull
    @Column(name = "description")
    private String description;

    @Column(name = "transactional")
    private boolean transactional;

}
