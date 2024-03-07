package com.smartcontact.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactid;
	private String contactname;
	private String secondname;
	private String work;
	private String email;
	
	@Size(min = 10,max = 10,message = "Size length should be equal to 10")
	private String phone;
	private String contactimageurl;
	@Column(length = 500)
	private String address;
	
	@ManyToOne
	private User user;
	
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Contact(int contactid, String contactname, String secondname, String work, String email, String phone,
			String contactimageurl, String address,User user) {
		super();
		this.contactid = contactid;
		this.contactname = contactname;
		this.secondname = secondname;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.contactimageurl = contactimageurl;
		this.address = address;
	}
	public Contact(String contactname, String secondname, String work, String email, String phone,
			String contactimageurl, String address,User user) {
		super();
		this.contactname = contactname;
		this.secondname = secondname;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.contactimageurl = contactimageurl;
		this.address = address;
		this.user = user;
	}
	public int getContactid() {
		return contactid;
	}
	public void setContactid(int contactid) {
		this.contactid = contactid;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContactimageurl() {
		return contactimageurl;
	}
	public void setContactimageurl(String contactimageurl) {
		this.contactimageurl = contactimageurl;
	}
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Contact [contactid=" + contactid + ", contactname=" + contactname + ", secondname=" + secondname
				+ ", work=" + work + ", email=" + email + ", phone=" + phone + ", contactimageurl=" + contactimageurl
				+ ", address=" + address + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.contactid == ((Contact)obj).getContactid();
	}
	
	
	
	
}
