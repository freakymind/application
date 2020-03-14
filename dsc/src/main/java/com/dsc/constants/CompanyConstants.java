package com.dsc.constants;

import java.security.SecureRandom;

public class CompanyConstants {
	
	public static final String COMP_REF_ID = "DSC-COMP-";
	public static final String USER_ID = "DSC-USER-";
	public static final String DIST_ID = "DSC-DIST-";

	public static final String REGISTER_SUCCESS = "Company Registration is Successful. You will receive an email once your account is verified by Admin.";
	public static final String COMPANYUSER_SUCCESS = "Company User added successfully !";
	public static final String COMPANYUSER_UPDATED_SUCCESS = "User details updated successfully !";
	public static final String DISTRIBUTOR_SUCCESS = "Distributor added successfully !";
	public static final String USER_DELETED_SUCCESS = "User deleted successfully !";
	
	
	public static final String NO_RECORDS = "No records found!";
	public static final String FAILED = "Company Registration is Failed.";
	public static final String USER_EXISTS = "User Already Exists with the email";
	public static final String USER_UPDATE_FAILED = "User not updated";
	public static final String USER_DELETE_FAILED = "User not deleted";

	public static final String SUCCESS = "0";
	public static final String FAIL = "1";
	public static final String REQUEST_EMPTY = "Please Enter All the required Details";

	public static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static final SecureRandom rnd = new SecureRandom();

}
