package com.distribute.dsc.serviceImpl;

import com.distribute.dsc.dao.CompanyDao;
import com.distribute.dsc.handler.RegisterCompanyHandler;
import com.distribute.dsc.mapper.CompanyMapper;
import com.distribute.dsc.model.RegisterCompany;
import com.distribute.dsc.model.UserResponse;
import com.distribute.dsc.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

@Service
public class CompanyServiceImpl implements CompanyService {

    public static  Integer SUCCESS = 1;
    public static  String REGISTERSUCCESS = "Company Registration Success.Please wait for the confirmation mail.";
    public static  String USEREXISTS ="User Already Exists with the email";
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final String secretKey = "h@rryPotter";
    static final String salt = "h@rryPotter";
    static Cipher cipher;
    static SecureRandom rnd = new SecureRandom();


    @Autowired
    CompanyDao database;



    @Override
    public UserResponse registerCompany(RegisterCompanyHandler requestBody) throws Exception {
        UserResponse response = new UserResponse();
        RegisterCompany mappedDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
        RegisterCompany details = database.findByUserEmail(requestBody.getEmail());
        System.out.println(details);
        if(details == null){
            String plainText = null;
            if(requestBody.getPassword()=="" || requestBody.getPassword() == null){
            String generatedPassword = generatecompanyReference();
                plainText = generatedPassword;

            }
            else{

                plainText = requestBody.getPassword();

            }

//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128);
//            SecretKey secretKey = keyGenerator.generateKey();
//
//            cipher = Cipher.getInstance("AES");

            String encryptedPassword = encrypt(plainText,secretKey);
            mappedDetails.getUser().get(0).setPassword(encryptedPassword);



            String generatedCompanyRef = generatecompanyReference();
            mappedDetails.getUser().get(0).setCompanyRef(generatedCompanyRef);
            mappedDetails.getUser().get(0).setRole("COMPANY_ADMIN");
            mappedDetails.getUser().get(0).setActive(false);
            mappedDetails.getCompany().setCompanyRef(generatedCompanyRef);
            mappedDetails.getCompany().setStatus(false);
            database.save(mappedDetails);

            response.setData(mappedDetails);
            response.setMessage(REGISTERSUCCESS);
            response.setStatus(SUCCESS);
            return response;


        }
        else{
            response.setStatus(SUCCESS);
            response.setMessage(USEREXISTS);
            return response;
        }
    }


    public  String generatecompanyReference() {

        Random r = new java.util.Random ();
        String s = Long.toString (r.nextLong () & Long.MAX_VALUE, 24);
        return s;
    }



//    public String encryptPassword(String plainText) throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//
//        keyGenerator.init(128);
//        SecretKey secretKey = keyGenerator.generateKey();
//        cipher = Cipher.getInstance("AES");
//        String encryptedText = encrypt(plainText, secretKey);
//        return encryptedText;
//    }


//    public static String encrypt(String plainText, SecretKey secretKey)
//            throws Exception {
//        byte[] plainTextByte = plainText.getBytes();
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        byte[] encryptedByte = cipher.doFinal(plainTextByte);
//        Base64.Encoder encoder = Base64.getEncoder();
//        String encryptedText = encoder.encodeToString(encryptedByte);
//        return encryptedText;
//    }


    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }







}
