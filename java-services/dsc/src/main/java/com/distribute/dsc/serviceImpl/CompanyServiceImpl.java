package com.distribute.dsc.serviceImpl;

import com.distribute.dsc.dao.CompanyDao;
import com.distribute.dsc.handler.RegisterCompanyHandler;
import com.distribute.dsc.mapper.CompanyMapper;
import com.distribute.dsc.model.RegisterCompany;
import com.distribute.dsc.model.UserResponse;
import com.distribute.dsc.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class CompanyServiceImpl implements CompanyService {

    public static  Integer SUCCESS = 1;
    public static  String REGISTERSUCCESS = "Company Registration Success.Please wait for the confirmation mail.";
    public static  String USEREXISTS ="User Already Exists with the email";
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();


    @Autowired
    CompanyDao database;



    @Override
    public UserResponse registerCompany(RegisterCompanyHandler requestBody) {
        UserResponse response = new UserResponse();
        RegisterCompany mappedDetails = CompanyMapper.mapAllCompanyDetails(requestBody);
        RegisterCompany details = database.findByUserEmail(requestBody.getEmail());


//        {
//            "fullname": "uppu yashwanth",
//                "email": "yashwanth.uppu@ojas-it.com",
//                "mobile": "8333965045",
//                "address": "Hyderabad",
//                "country": "India",
//                "role": null,
//                "password": "Ojas1525",
//                "companyRef": null,
//                "isActive": null
//        }

        if(details == null){
            if(requestBody.getPassword()=="" || requestBody.getPassword() == null){
                mappedDetails.getUser().get(0).setPassword("Ojas1525");
            }
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
}
