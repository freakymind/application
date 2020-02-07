package com.distribute.dsc.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection = "user")
public class RegisterCompany {


    private ArrayList<User> user;
    private Company company;

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

    @Data
    public static class Company {
        private String company_name;
        private String company_address;
        private String company_mobile;
        private String company_email;
        private String companyRef;
        private boolean status;

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

        public String getCompany_email() {
            return company_email;
        }

        public void setCompany_email(String company_email) {
            this.company_email = company_email;
        }

        public String getCompanyRef() {
            return companyRef;
        }

        public void setCompanyRef(String companyRef) {
            this.companyRef = companyRef;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    @Data
    public static class User {
        private String fullname;
        private String email;
        private String mobile;
        private String address;
        private String country;
        private String role;
        private String password;
        private String companyRef;
        private boolean isActive;

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCompanyRef() {
            return companyRef;
        }

        public void setCompanyRef(String companyRef) {
            this.companyRef = companyRef;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
