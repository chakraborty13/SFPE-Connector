package com.f5.salesforce.emp.connector.okta;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.f5.salesforce.emp.connector.properties.EnvMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class OktaClient {
	
	private static final Logger logger = LoggerFactory.getLogger(OktaClient.class);
	private static Client client;
	
	private static EnvMap envMap = new EnvMap();
	private static String oktaURL = envMap.getEnv("OKTA_URL");
	private static String apiToken = envMap.getEnv("API_TOKEN");
	



	public static Client getClient() {
		try {
			client = Clients
					.builder()
					.setOrgUrl(oktaURL)
					.setClientCredentials(new TokenClientCredentials(apiToken))
					.build();
		} catch (ExceptionInInitializerError ie) {
			logger.info(ie.getCause().toString());
		} catch (Exception e) {
			logger.info(e.getCause().toString());
		}		
		logger.info("Created client for okta URL: {}",oktaURL);
		return client;
	}
}
