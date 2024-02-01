package com.vue.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("JwtService")
public class JwtServiceImpl implements JwtService {
	
	private String secretKey = "sfdhgdfg@@@dgfgd4538!!fdghdbdfgkzfvcgdu35##235jdfgbd3345f";
	
	@Override
	public String getToken(String key, Object value) {
		
		Date expTime = new Date();
		expTime.setTime(expTime.getTime() + 1000 * 60 * 30);
		byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
		Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
		
		Map<String, Object> headerMap = new HashMap();
		headerMap.put("typ", "JWT");
		headerMap.put("alg", "HS256");
		
		Map<String, Object> map = new HashMap();
		map.put(key, value);
		
		JwtBuilder builder = Jwts.builder().setHeader(headerMap)
				.setClaims(map)
				.setExpiration(expTime)
				.signWith(signKey, SignatureAlgorithm.HS256);
		
		
		return builder.compact();
	}

	@Override
	public Claims getClaims(String token) {
		if(token != null && !"".equals(token)) {
			try {
				byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
				Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
				Claims claims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJwt(token).getBody();
				
				return claims;
			}catch(ExpiredJwtException e) {
				
			}catch(JwtException e) {
				
			}
		}
		return null;
	}

	@Override
	public boolean isVaild(String token) {
		
		return this.getClaims(token) != null;
	}

	@Override
	public int getId(String token) {
		Claims claims = this.getClaims(token);
		
		if(claims != null) {
			return Integer.parseInt(claims.get("id").toString());
		}
		
		return 0;
	}
}
