package com.dsc.model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "company")
public class RegisterCompany {

	@Id
	private String id;
	private Company company;
	private ArrayList<User> user;
	private ArrayList<Distributor> distributor;

	public RegisterCompany() {
		super();
	}

	public RegisterCompany(String id, ArrayList<User> user, Company company) {
		super();
		this.id = id;
		this.user = user;
		this.company = company;
	}

	public void addUser(ArrayList<User> user) {
		this.user.addAll(user);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<User> getUser() {
		return user;
	}

	public void setUser(ArrayList<User> user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ArrayList<Distributor> getDistributor() {
		return distributor;
	}

	public void setDistributor(ArrayList<Distributor> distributor) {
		this.distributor = distributor;
	}

	public static class Company {
		private String company_name;
		private String company_address;
		private String company_mobile;
		@Field("company_email")
		private String email;
		private String company_ref;
		private boolean company_status;
		private Date created_on;
		private Date updated_on;

		public Company() {
			super();
		}

		public Company(String company_name, String company_address, String company_mobile, String email,
				String company_ref, boolean company_status, Date created_on, Date updated_on) {
			super();
			this.company_name = company_name;
			this.company_address = company_address;
			this.company_mobile = company_mobile;
			this.email = email;
			this.company_ref = company_ref;
			this.company_status = company_status;
			this.created_on = created_on;
			this.updated_on = updated_on;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public String getCompany_address() {
			return company_address;
		}

		public void setCompany_address(String company_address) {
			this.company_address = company_address;
		}

		public String getCompany_mobile() {
			return company_mobile;
		}

		public void setCompany_mobile(String company_mobile) {
			this.company_mobile = company_mobile;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCompany_ref() {
			return company_ref;
		}

		public void setCompany_ref(String company_ref) {
			this.company_ref = company_ref;
		}

		public boolean isCompany_status() {
			return company_status;
		}

		public void setCompany_status(boolean company_status) {
			this.company_status = company_status;
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

	}

	public static class User {
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
		private boolean user_status;
		private Date created_on;
		private Date updated_on;

		public User() {
			super();
		}

		public User(String user_name, String email, String user_mobile, String user_address, String user_country,
				String role, String password, boolean user_status, Date created_on, Date updated_on) {
			super();
			this.user_name = user_name;
			this.email = email;
			this.user_mobile = user_mobile;
			this.user_address = user_address;
			this.user_country = user_country;
			this.role = role;
			this.password = password;
			this.user_status = user_status;
			this.created_on = created_on;
			this.updated_on = updated_on;
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

		public boolean isUser_status() {
			return user_status;
		}

		public void setUser_status(boolean user_status) {
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

	}

	public static class Distributor {

		private String company_ref;
		private String distributor_name;

		public Distributor() {
			super();
		}

		public Distributor(String company_ref, String distributor_name) {
			super();
			this.company_ref = company_ref;
			this.distributor_name = distributor_name;
		}

		public String getCompany_ref() {
			return company_ref;
		}

		public void setCompany_ref(String company_ref) {
			this.company_ref = company_ref;
		}

		public String getDistributor_name() {
			return distributor_name;
		}

		public void setDistributor_name(String distributor_name) {
			this.distributor_name = distributor_name;
		}

	}

}
