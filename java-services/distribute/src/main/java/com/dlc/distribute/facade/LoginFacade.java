package com.dlc.distribute.facade;

import com.dlc.distribute.model.UserLoginRequest;
import com.dlc.distribute.model.UserLoginResponse;

public interface LoginFacade {
    UserLoginResponse loginUser(UserLoginRequest loginRequest);

}
