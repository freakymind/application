package com.dsc.model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "user")
public class User {

	@Id
	private String id;
	private ArrayList<UserDetails> userdetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<UserDetails> getUserdetails() {
		return userdetails;
	}

	public void setUserdetails(ArrayList<UserDetails> userdetails) {
		this.userdetails = userdetails;
	}

	public static class UserDetails {
		private String user_id;
		private String user_name;
		@Field("user_email")
		private String email;
		private String user_mobile;
		private String user_address;
		private String user_country;
		@Field("user_role")
		private String role;
		@Field("user_password")
		private String password;
		private String user_status;
		private Date created_on;
		private Date updated_on;

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getUser_name() {
			return user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getUser_mobile() {
			return user_mobile;
		}

		public void setUser_mobile(String user_mobile) {
			this.user_mobile = user_mobile;
		}

		public String getUser_address() {
			return user_address;
		}

		public void setUser_address(String user_address) {
			this.user_address = user_address;
		}

		public String getUser_country() {
			return user_country;
		}

		public void setUser_country(String user_country) {
			this.user_country = user_country;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUser_status() {
			return user_status;
		}

		public void setUser_status(String user_status) {
			this.user_status = user_status;
		}

		public Date getCreated_on() {
			return created_on;
		}

		public void setCreated_on(Date created_on) {
			this.created_on = created_on;
		}

		public Date getUpdated_on() {
			return updated_on;
		}

		public void setUpdated_on(Date updated_on) {
			this.updated_on = updated_on;
		}

		@Override
		public String toString() {
			return "User [user_id=" + user_id + ", user_name=" + user_name + ", email=" + email + ", user_mobile="
					+ user_mobile + ", user_address=" + user_address + ", user_country=" + user_country + ", role="
					+ role + ", password=" + password + ", user_status=" + user_status + ", created_on=" + created_on
					+ ", updated_on=" + updated_on + "]";
		}
	}
}
