package com.distribute.dsc.controller;

import com.distribute.dsc.handler.RegisterCompanyHandler;
import com.distribute.dsc.model.UserResponse;
import com.distribute.dsc.service.CompanyService;
import com.distribute.dsc.utility.ErrorResponse;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@RestController
@EnableAutoConfiguration
public class CompanyController {

    @Autowired
    CompanyService companyService;

    public static Integer FAIL = 1;
    public static String REQUESTEMPTY = "Please Enter All the required Details";
    ErrorResponse errorResponse = new ErrorResponse();
    UserResponse response = null;

    Logger logger = Logger.getLogger(this.getClass());

@RequestMapping(value="/registerCompany",method = RequestMethod.POST)
    public ResponseEntity<Object> registerCompany(@RequestBody RegisterCompanyHandler requestBody, HttpServletRequest httpRequest, HttpServletResponse httpResponse){
    if(requestBody == null || requestBody.getCompany_name()=="" ||requestBody.getCompany_address() == "" ||requestBody.getCompany_email() == "" ||requestBody.getCompany_name() == "" ||requestBody.getAddress() == "" ||requestBody.getCountry() == "" ||requestBody.getEmail() == "" ||requestBody.getFullname() == "" ||requestBody.getMobile() == ""  ){
        logger.error("The Request Body is Empty");
        errorResponse.setMessage(REQUESTEMPTY);
        errorResponse.setStatus(FAIL);
        errorResponse.setData(null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    response = companyService.registerCompany(requestBody);
    return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
