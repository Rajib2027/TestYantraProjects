package com.te.sportbookingsystem.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.te.sportbookingsystem.entity.DefaultUser;
import com.te.sportbookingsystem.repository.DefaultUserResposotary;



@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private DefaultUserResposotary userRespositary;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DefaultUser user = userRespositary.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}
		return new CustomUserDetails(user);
	}

}
