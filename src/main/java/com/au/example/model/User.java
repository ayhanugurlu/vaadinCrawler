package com.au.example.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User  {

	@Id
	@GeneratedValue
	private Long id;

	private String email;

	private String firstName;

	private String lastName;
	
	private String password;
	
	public User() {	
	}
	public User(String email,String firstName, String lastName,String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}


	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return String.format("User[id=%d, email='%s', firstName='%s', lastName='%s']", id,email,
				firstName, lastName);
	}

}