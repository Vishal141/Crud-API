package com.crud.api.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Email(message = "email is not valid")
    private String username;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotNull
    @Size(min = 6,message = "password must have characters between 6 and 20")
    private String password;

    @NotNull
    @Size(min = 10,max = 10,message = "contact number is not valid")
    private String contactNo;

    public User(){}

    public User(String username, String name, String password, String contactNo) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.contactNo = contactNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
