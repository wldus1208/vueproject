package com.vue.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
	String getToken(String key, Object value);
	
	Claims getClaims(String token);
	
	boolean isVaild(String token);
	
	int getId(String token);
}
