package com.f5.salesforce.emp.connector.okta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import com.okta.sdk.resource.user.UserList;
import com.f5.salesforce.emp.connector.model.EventMessage;

public class OktaSdk {

	private final Logger logger = LoggerFactory.getLogger(OktaSdk.class);
	private Client client; 

	private static final String PARTNER_ID = "3274"; // 3274 is Partner Profile Id in LMS
	private static final String CUSTOMER_ID = "3273"; // 3273 is Customer Profile Id in LMS

	
	public OktaSdk(Client client) {
		this.client = client;
	}
	
	private User getUserByEmail(String email) {
		User user = null;
		UserList users = client.listUsers(email, null, null, null, null);
		String tmpEmail = null;
		for (User tmpUser : users) {
			tmpEmail = tmpUser.getProfile().getEmail();
			if (email.equals(tmpEmail)) {
				user = tmpUser;
			}
		}
		return user;
	}

	private User getUser(String userId) {
		return client.getUser(userId);
	}

	public String getEmail(String userId) {
		return getUser(userId).getProfile().getEmail();
	}

	public User createUser(String email, String firstName, String lastName) {
		return UserBuilder.instance().setEmail(email).setFirstName(firstName).setLastName(lastName)
				.buildAndCreate(client);
	}

	public User updateUser(String email, String firstName, String lastName, String divisionId) {
		User user = getUserByEmail(email);
		if (user.isEmpty() || user == null) {
			return null;
		} else {
			user.getProfile().setFirstName(firstName);
			user.getProfile().setLastName(lastName);
			user.getProfile().setLogin(email);
			user.getProfile().setEmail(email);
			if (divisionId.equals(PARTNER_ID)) {
				user.getProfile().put("partnerFlag", true);
			} else {
				user.getProfile().put("partnerFlag", false);
			}
			try {
				user = user.update();
			} catch (Exception e) {
				logger.info(e.toString());
			}
			return user;
		}
	}

	// following section extracts values from event message

	public EventMessage getEventMessage(String payloadStr) {

		Gson gson = new Gson();
		EventMessage msg = gson.fromJson(payloadStr, EventMessage.class);
		return msg;

	}

	public String getPayloadEmail(String payloadStr) {
		return getEventMessage(payloadStr).getPayload().getEmail_Work__c();
	}

	public String getPayloadFirstname(String payloadStr) {
		return getEventMessage(payloadStr).getPayload().getFirstName__c();
	}

	public String getPayloadLastname(String payloadStr) {
		return getEventMessage(payloadStr).getPayload().getLastName__c();
	}

	public String getPayloadDivisionId(String payloadStr) {
		return getEventMessage(payloadStr).getPayload().getDivision_ID__c();
	}

}