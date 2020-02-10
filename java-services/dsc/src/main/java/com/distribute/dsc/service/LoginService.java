package com.distribute.dsc.service;

import com.distribute.dsc.handler.LoginHandler;
import com.distribute.dsc.model.UserResponse;

public interface LoginService {
    UserResponse userLogin(LoginHandler requestBody) throws Exception;
}
