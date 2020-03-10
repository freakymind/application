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
	private ArrayList<User> userid;
	private ArrayList<Distributor> distributor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ArrayList<User> getUserid() {
		return userid;
	}

	public void setUserid(ArrayList<User> userid) {
		this.userid = userid;
	}

	public ArrayList<Distributor> getDistributor() {
		return distributor;
	}

	public void setDistributor(ArrayList<Distributor> distributor) {
		this.distributor = distributor;
	}

	public static class Company {
		private String comp_admin_id;
		private String company_name;
		private String company_address;
		private String company_mobile;
		@Field("company_email")
		private String email;
		@Field("company_id")
		private String company_ref;
		private boolean company_status;
		private Date created_on;
		private Date updated_on;

		public String getComp_admin_id() {
			return comp_admin_id;
		}

		public void setComp_admin_id(String comp_admin_id) {
			this.comp_admin_id = comp_admin_id;
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

		private String user_id;

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

	}

	public static class Distributor {

		private String dist_id;
		private String company_ref;

		public String getDist_id() {
			return dist_id;
		}

		public void setDist_id(String dist_id) {
			this.dist_id = dist_id;
		}

		public String getCompany_ref() {
			return company_ref;
		}

		public void setCompany_ref(String company_ref) {
			this.company_ref = company_ref;
		}

	}

}
