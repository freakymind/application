package com.distribute.dsc.serviceImpl;

import com.distribute.dsc.dao.CompanyDao;
import com.distribute.dsc.handler.LoginHandler;
import com.distribute.dsc.model.RegisterCompany;
import com.distribute.dsc.model.UserLogin;
import com.distribute.dsc.model.UserResponse;
import com.distribute.dsc.service.LoginService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    CompanyDao database;

UserResponse response = new UserResponse();
String NOUSERFOUND = "NO USER FOUND WITH THE EMAIL PROVIDED";
    String LOGINSUCCESS = "LOGGED IN SUCCESSFULLY";
    String INVALIDPASSWORD = "INVALID PASSWORD";
    String USERNOTACTIVE = "USER NOT ACTIVE";
    String COMPANYNOTACTIVE = "COMPANY NOT ACTIVE";
    String USERANDCOMPINACTIVE = "USER AND COMPANY NOT ACTIVE";
    static Cipher cipher;
    static final String secretKey = "h@rryPotter";
    static final String salt = "h@rryPotter";
Integer SUCCESS = 0;
Integer FAIL = 1;

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public UserResponse userLogin(LoginHandler requestBody) throws Exception {
        RegisterCompany userDetails = database.findByUserLoginEmail(requestBody.getEmail());
        String encryptedPassword = "";

        if (userDetails != null) {
            logger.info(userDetails);
            UserLogin user = new UserLogin();
            ArrayList<RegisterCompany.User> usersList = userDetails.getUser();
            logger.info(usersList);
            for (int i = 0; i < usersList.size(); i++) {
                logger.info("Hiiii");
                logger.info(usersList.get(i));
                if (usersList.get(i).getEmail().equalsIgnoreCase(requestBody.getEmail())) {
                    logger.info("Innnn");
                    user.setUser(usersList.get(i));
                    user.setCompany(userDetails.getCompany());
                    encryptedPassword = usersList.get(i).getPassword();
                }
            }
            logger.info(user.getUser() + "user");
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128);
//            SecretKey secretKey = keyGenerator.generateKey();
//            cipher = Cipher.getInstance("AES");


            String decryptedText = decrypt(encryptedPassword, secretKey);
            logger.info(decryptedText + "decrypted");

            if (user.getUser().isActive() == true && user.getCompany().isStatus() == true) {
                if (decryptedText.equals(requestBody.getPassword())) {
                    response.setData(user);
                    response.setStatus(SUCCESS);
                    response.setMessage(LOGINSUCCESS);
                    return response;
                } else {

                    response.setData(null);
                    response.setStatus(FAIL);
                    response.setMessage(INVALIDPASSWORD);
                    return response;

                }
            } else {

                if (user.getCompany().isStatus() == false && user.getUser().isActive() == false) {

                    response.setData(null);
                    response.setStatus(FAIL);
                    response.setMessage(USERANDCOMPINACTIVE);
                    return response;
                }
               else if (user.getUser().isActive() == false) {
                    response.setData(null);
                    response.setStatus(FAIL);
                    response.setMessage(USERNOTACTIVE);
                    return response;
                } else if (user.getCompany().isStatus() == false) {
                    response.setData(null);
                    response.setStatus(FAIL);
                    response.setMessage(COMPANYNOTACTIVE);
                    return response;
                }


            }

        } else {
            response.setStatus(FAIL);
            response.setMessage(NOUSERFOUND);
            response.setData(null);
            return response;
        }
        return null;
    }






    public static String decrypt(String strToDecrypt, String secret) {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}
