package com.icebergarts.carwashagent.security.oauth2.user;



import java.util.Map;

import com.icebergarts.carwashagent.exception.OAuth2AuthenticationProcessingException;
import com.icebergarts.carwashagent.model.AuthProvider;

public class OAuth2UserInfoFactory {
	
	private OAuth2UserInfoFactory() {
		throw new IllegalStateException("OAuth Utility class");
	}

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
