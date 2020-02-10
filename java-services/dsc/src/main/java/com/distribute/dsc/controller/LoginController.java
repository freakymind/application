package com.distribute.dsc.controller;

import com.distribute.dsc.handler.LoginHandler;
import com.distribute.dsc.handler.RegisterCompanyHandler;
import com.distribute.dsc.model.UserResponse;
import com.distribute.dsc.service.LoginService;
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
public class LoginController {

    @Autowired
    LoginService loginService;

    ErrorResponse errorResponse = new ErrorResponse();
    UserResponse response = null;
    String REQUESTEMPTY = "The Email and Password are both Empty.Please fill the required field(s).";
    String EMAILEMPTY = "The Email field is Empty.Please fill the required field(s).";
    String PASSWORDEMPTY = "The Password field is Empty.Please fill the required field(s).";
    Integer FAIL = 1;

    Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value="/userLogin",method = RequestMethod.POST)
    public ResponseEntity<Object> userLogin(@RequestBody LoginHandler requestBody, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        if(requestBody==null || requestBody.getEmail()=="" && requestBody.getPassword()==""){
        errorResponse.setMessage(REQUESTEMPTY);
        errorResponse.setStatus(FAIL);
        errorResponse.setData(null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else if(requestBody.getEmail()=="" || requestBody.getEmail()==null){
            errorResponse.setMessage(EMAILEMPTY);
            errorResponse.setStatus(FAIL);
            errorResponse.setData(null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else if(requestBody.getPassword()=="" || requestBody.getPassword()==null){
            errorResponse.setMessage(PASSWORDEMPTY);
            errorResponse.setStatus(FAIL);
            errorResponse.setData(null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        response = loginService.userLogin(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
