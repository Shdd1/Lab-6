package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.Mac;
import java.util.Date;

@Data
@AllArgsConstructor
public class Employees {

    @NotNull(message = "id Cannot be null.")
    @Size(min = 3,message = "ID must be more than 3")
    private String id;


    @NotNull(message = "name Cannot be null.")
    @Size(min = 5,message ="The name must be more than five characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "error: name cannot be a number")
    private String name;


    @Email(message = " Must be a valid email format. ")
    private String email;


    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and consist of exactly 10 digits")
    private String phoneNumber;


    @NotNull(message = "age Cannot be null. ")
    @Positive(message="Age must be a positive number")
    @Min(value = 26,message = "Age must be more than 25")
    @Max(value = 40)
    private int age;


    @NotNull(message = "position Cannot be null. ")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator'")
    private String position;

    //check
    @AssertFalse
    private Boolean onLeave;


    @NotNull(message = "Hire Date Cannot be null. ")
    @PastOrPresent(message = "should be a date in the past or the present. ")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date  hireDate;

    @NotNull(message = " Annual Leave Cannot be null. ")
    @Positive(message = "Annual Leave Must be a positive number.")
    private int annualLeave;

}
