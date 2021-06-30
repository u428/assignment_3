package com.assignment.io.assignment_3.Model.ForLogin;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CustomerSignUp {

    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    @NotEmpty
    public String country;

    @NotNull
    @NotEmpty
    public String address;

    @NotNull
    public Integer phone;

    @NotNull
    @NotEmpty
    public String password;

}
