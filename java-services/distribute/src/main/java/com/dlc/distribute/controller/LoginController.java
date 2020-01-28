package com.dlc.distribute.controller;

import com.dlc.distribute.facade.LoginFacade;
import com.dlc.distribute.model.UserLoginRequest;
import com.dlc.distribute.model.UserLoginResponse;
import com.dlc.distribute.utility.ErrorResponse;
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
    public static Integer SUCCESS = 0;
    public static Integer FAIL = 1;
    public static String REQUESTEMPTY = "Please Enter Email and Password,Email and Password Cannot be Empty";
    public static String EMAILEMPTY = "Please Enter Email,Email  Cannot be Empty";
    public static String PASSWORDEMPTY = "Please Enter Password,Password Cannot be Empty";

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private LoginFacade loginService;

    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest loginRequest, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception{
        ErrorResponse errorResponse = new ErrorResponse();
        UserLoginResponse response = null;

            logger.error("inside controller");
            if(loginRequest.getEmail() == null && loginRequest.getPassword()==null || loginRequest.getEmail() == "" && loginRequest.getPassword()== ""){
                logger.error("The Request Body is Empty");
                errorResponse.setMessage(REQUESTEMPTY);
                errorResponse.setStatus(FAIL);
                errorResponse.setData(null);
                return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else if(loginRequest.getEmail() == null || loginRequest.getEmail() == "" ){
                errorResponse.setMessage(EMAILEMPTY);
                errorResponse.setStatus(FAIL);
                errorResponse.setData(null);
                return new ResponseEntity<>(errorResponse,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else if(loginRequest.getPassword() == null || loginRequest.getPassword() == "") {
                errorResponse.setMessage(PASSWORDEMPTY);
                errorResponse.setStatus(FAIL);
                errorResponse.setData(null);
                return new ResponseEntity<>(errorResponse,HttpStatus.UNPROCESSABLE_ENTITY);
            }


            response = loginService.loginUser(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);


    }

}
