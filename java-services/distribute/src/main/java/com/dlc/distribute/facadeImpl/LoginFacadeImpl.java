package com.dlc.distribute.facadeImpl;

import com.dlc.distribute.dao.LoginDao;
import com.dlc.distribute.facade.LoginFacade;
import com.dlc.distribute.model.User;
import com.dlc.distribute.model.UserLoginRequest;
import com.dlc.distribute.model.UserLoginResponse;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginFacadeImpl implements LoginFacade {
    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private LoginDao userData;

    public static String NOUSERFOUND = "No User Found";
    public static String INVALIDPASSWORD = "Please Enter Valid Password";
    public static String LOGINSUCCESS = "Logged In Successfully";
    public static String INTERNALERROR = "Something went Wrong";

    public static Integer FAIL = 1;
    public static Integer SUCCESS = 0;


    @Override
    public UserLoginResponse loginUser(UserLoginRequest loginRequest) {
        try {
        UserLoginResponse response = new UserLoginResponse();
        logger.debug("vallll" + loginRequest.getEmail());
        User validateEmail = userData.findByEmail(loginRequest.getEmail());
        logger.debug("vallll" + validateEmail);
        if (validateEmail == null) {
            response.setStatus(FAIL);
            response.setMessage(NOUSERFOUND);
            return response;
        } else {
            logger.debug("password"+loginRequest.getPassword());
            logger.debug("password"+validateEmail.getPassword());
            if (validateEmail.getPassword().equalsIgnoreCase(loginRequest.getPassword())) {
                response.setStatus(SUCCESS);
                response.setMessage(LOGINSUCCESS);
                response.setData(validateEmail);
                return response;
            } else {
                response.setStatus(FAIL);
                response.setMessage(INVALIDPASSWORD);
                return response;

            }
        }


        }
        catch (Exception e){
            logger.error("exception" +  e );
            UserLoginResponse response = new UserLoginResponse();
            response.setMessage(INTERNALERROR);
            response.setStatus(FAIL);
            return response;
        }


    }
}