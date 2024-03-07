package com.smartcontact.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   
   private int user_id;
   
   @NotBlank(message = "Name field is required")
   @Size(message = "Length should be between 3 and 30")
   private String username;
   @Column(unique = true)
   private String useremail;
   private String userpassword;
   private String role;
   private String userimageurl;
   private boolean enabled;
   @Column(length = 5000)
   private String description;
   
   @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",orphanRemoval = true)
   
   private List<Contact> contacts = new ArrayList<>();
   
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int user_id, String username, String useremail, String userpassword, String role, String userimageurl,
			boolean enabled, String description,List<Contact> contacts) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.useremail = useremail;
		this.userpassword = userpassword;
		this.role = role;
		this.userimageurl = userimageurl;
		this.enabled = enabled;
		this.description = description;
		this.contacts = contacts;
	}

	public User(String username, String useremail, String userpassword, String role, String userimageurl, boolean enabled,
			String description) {
		super();
		this.username = username;
		this.useremail = useremail;
		this.userpassword = userpassword;
		this.role = role;
		this.userimageurl = userimageurl;
		this.enabled = enabled;
		this.description = description;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserImageurl() {
		return userimageurl;
	}

	public void setUserImageurl(String userimageurl) {
		this.userimageurl = userimageurl;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username + ", useremail=" + useremail + ", userpassword="
				+ userpassword + ", role=" + role + ", userimageurl=" + userimageurl + ", enabled=" + enabled
				+ ", description=" + description + ", contacts=" + contacts + "]";
	}

	
	
	
   
   
   
   
}
