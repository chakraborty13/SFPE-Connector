package com.f5.salesforce.emp.connector.model;



/**
 * {
  "schema": "XOfis_0pU9KCsiImE2lD5Q",
  "payload": {
    "Company_Organization_Name__c": "Westcon Group Pty Ltd",
    "Division_ID__c": "3274",
    "Share_to_Wi__c": true,
    "CreatedById": "005R000000BqvZwIAJ",
    "Email_Work__c": "consatya1935-xxx@mailinator.com",
    "Partner_Level__c": "Distributor",
    "CreatedDate": "2023-02-02T17:27:29.896Z",
    "LastName__c": "chakra",
    "Account_ID__c": "0015000000SO8jRAAT",
    "Contact_ID__c": "003R000001mCSRBIA4",
    "Theatre__c": "APAC",
    "FirstName__c": "KATE_DEAR"
  },
  "event": {
    "replayId": 3933214
  }
}
 */

public class EventMessage {

  private String schema;
  private Payload payload;
  private Event event;
  
  public EventMessage(String schema, Payload payload, Event event) {
    this.schema = schema;
    this.payload = payload;
    this.event = event;
  }
  public String getSchema() {
    return schema;
  }
  public void setSchema(String schema) {
    this.schema = schema;
  }
  public Payload getPayload() {
    return payload;
  }
  public void setPayload(Payload payload) {
    this.payload = payload;
  }
  public Event getEvent() {
    return event;
  }
  public void setEvent(Event event) {
    this.event = event;
  }

  
  
}
