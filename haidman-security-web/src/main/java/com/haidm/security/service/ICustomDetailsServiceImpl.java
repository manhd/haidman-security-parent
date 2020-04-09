package com.haidm.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface ICustomDetailsServiceImpl {

    UserDetails getUserDetails(String author);
}
