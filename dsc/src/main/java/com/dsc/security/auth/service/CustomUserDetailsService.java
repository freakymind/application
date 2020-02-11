package com.dsc.security.auth.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dsc.dao.RegisterCompnayDao;
import com.dsc.model.RegisterCompany;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private RegisterCompnayDao regDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		RegisterCompany userDetails = regDao.findByUserEmail(email);

		if (userDetails != null) {
			List<GrantedAuthority> authorities = getUserAuthority(userDetails.getUser().get(0).getRole());
			System.out.println("Authorities " + authorities);
			return getUserForDetails(userDetails, authorities);

		} else {
			throw new UsernameNotFoundException("user mail not found.");
		}
	}

	private List<GrantedAuthority> getUserAuthority(String role) {
		Set<GrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority(role));
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		System.out.println("GrantedAuth" + grantedAuthorities);
		return grantedAuthorities;
	}

	private UserDetails getUserForDetails(RegisterCompany userDetails, List<GrantedAuthority> authorities) {
		User user = new User(userDetails.getUser().get(0).getEmail(), userDetails.getUser().get(0).getPassword(),
				authorities);
		return user;
	}

}
