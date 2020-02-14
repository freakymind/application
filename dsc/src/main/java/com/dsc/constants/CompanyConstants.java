package com.dsc.constants;

import java.security.SecureRandom;

public class CompanyConstants {

	
	public static final String REGISTER_SUCCESS = "Company Registration is Successful. You will receive an email once your account is verified by Admin.";
	public static final String COMPANYUSER_SUCCESS = "Company User added successfully !";
	public static final String DISTRIBUTOR_SUCCESS = "Distributor added successfully !";
	
	public static final String FAILED = "Company Registration is Failed.";
	public static String USER_EXISTS = "User Already Exists with the email";
	
	public static Integer SUCCESS = 0;
	public static Integer FAIL = 1;
	public static String REQUEST_EMPTY = "Please Enter All the required Details";
	
	
	
	public static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static SecureRandom rnd = new SecureRandom();

}
