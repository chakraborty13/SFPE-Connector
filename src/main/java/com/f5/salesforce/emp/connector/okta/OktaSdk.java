package com.f5.salesforce.emp.connector.okta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.f5.salesforce.emp.connector.model.EventMessage;
import com.f5.salesforce.emp.connector.properties.EnvMap;
import com.google.gson.Gson;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import com.okta.sdk.resource.user.UserList;

public class OktaSdk {

  private final Logger logger = LoggerFactory.getLogger(OktaSdk.class);
  private Client client;
  private String payloadStr;

  private static final String PARTNER_ID = "3274"; // 3274 is Partner Profile Id in LMS
  private static final String CUSTOMER_ID = "3273"; // 3273 is Customer Profile Id in LMS

  public OktaSdk(String payloadStr) {
    EnvMap envMap = new EnvMap();
    String oktaURL = envMap.getEnv("OKTA_URL");
    String apiToken = envMap.getEnv("API_TOKEN");
    System.out.println("From OktaSdk class. Received parameters " + oktaURL + " " + apiToken + "\n" + payloadStr);
    try {
      this.client = Clients.builder()
          .setOrgUrl(oktaURL)
          .setClientCredentials(new TokenClientCredentials(apiToken))
          .build();
      this.payloadStr = payloadStr;
      System.out.println("Constructor SUCCESSFUL ++++++++++++++++++++++++++++++++++++++++++++++++++");
    } catch (Exception e) {
      System.out.println(e);
    }
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
    return UserBuilder.instance()
        .setEmail(email)
        .setFirstName(firstName)
        .setLastName(lastName)
        .buildAndCreate(client);
  }

  public User updateUser(String email, String firstName, String lastName, String divisionId) {
    System.out.println("Inside updateUser +++++++++++++++++++++++++++++++++++++++++++++++");
    User user = getUserByEmail(email);
    if (user.isEmpty() || user == null) {
      System.out.println("User is null +++++++++++++++++++++++++++++++++++++++++++++++");
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
      System.out.println("Just before user.update +++++++++++++++++++++++++++++++++++++++++++++++");
      try {
        user.update();
      } catch (Exception e) {
        System.out.println(e);
      }

      System.out.println("Just after user.update  +++++++++++++++++++++++++++++++++++++++++++++++");
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