package com.darjan.quizapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String username;
	
	private String fullName;
	
	@Email
	private String email;
	
	@OneToMany(mappedBy = "user")
	private List<Quiz> quizzes;
	
	@Enumerated(EnumType.STRING)
	private Provider provider;
	
	private boolean enabled = false;
	
	private String imageUrl;
	
	private String token;

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", fullName=" + fullName + ", email=" + email
				+ ", provider=" + provider + ", enabled=" + enabled + ", imageUrl=" + imageUrl + "]";
	}
	
	
}
