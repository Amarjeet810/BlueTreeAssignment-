package com.company.entity;





import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 @Getter
 @Setter
 @AllArgsConstructor
 @NoArgsConstructor
@Entity
@Table(name = "employees")
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	
	 @Email(message = "Invalid email format")
	 @NotBlank(message = "Email is mandatory")
	    
	@Column(nullable = false, unique = true)
	private String email;
	 @NotNull(message = "DOB cannot be null")
	private LocalDate dob;

	@DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
	@Column(nullable = false)
	private Double salary;
	@Column(nullable = false)
	private Boolean status;
	@Transient
	private Integer age;
	
	
	 // Method to dynamically calculate age from DOB
   // private Integer calculateAge() {
      //  if (this.dob != null) {
       //     return Period.between(this.dob, LocalDate.now()).getYears();
      //  }
      //  return null;
   // }
	
	

}
