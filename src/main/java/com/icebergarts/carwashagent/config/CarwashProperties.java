package com.icebergarts.carwashagent.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "carwash")
public class CarwashProperties {
	private final Auth auth = new Auth();
	private final OAuth2 oauth2 = new OAuth2();
	private final Properties properties = new Properties();

	public static class Auth {
		private String tokenSecret;
		private long tokenExpirationMsec;
		private String registrationVerificationApi;

		public String getTokenSecret() {
			return tokenSecret;
		}

		public void setTokenSecret(String tokenSecret) {
			this.tokenSecret = tokenSecret;
		}

		public long getTokenExpirationMsec() {
			return tokenExpirationMsec;
		}

		public void setTokenExpirationMsec(long tokenExpirationMsec) {
			this.tokenExpirationMsec = tokenExpirationMsec;
		}

		public String getRegistrationVerificationApi() {
			return registrationVerificationApi;
		}

		public void setRegistrationVerificationApi(String registrationVerificationApi) {
			this.registrationVerificationApi = registrationVerificationApi;
		}

	}

	public static final class OAuth2 {
		private List<String> authorizedRedirectUris = new ArrayList<>();

		public List<String> getAuthorizedRedirectUris() {
			return authorizedRedirectUris;
		}

		public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
			this.authorizedRedirectUris = authorizedRedirectUris;
			return this;
		}
	}
	
	public static final class Properties{
		private String mailSender;

		public String getMailSender() {
			return mailSender;
		}

		public void setMailSender(String mailSender) {
			this.mailSender = mailSender;
		}
		
		
	}

	public Auth getAuth() {
		return auth;
	}

	public OAuth2 getOauth2() {
		return oauth2;
	}
	
	public Properties getProperties() {
		return properties;
		
	}
}
