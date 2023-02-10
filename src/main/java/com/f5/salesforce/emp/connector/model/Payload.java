package com.f5.salesforce.emp.connector.model;

/**
 * {
 * "schema": "XOfis_0pU9KCsiImE2lD5Q",
 * "payload": {
 * "Company_Organization_Name__c": "Westcon Group Pty Ltd",
 * "Division_ID__c": "3274",
 * "Share_to_Wi__c": true,
 * "CreatedById": "005R000000BqvZwIAJ",
 * "Email_Work__c": "consatya1935-xxx@mailinator.com",
 * "Partner_Level__c": "Distributor",
 * "CreatedDate": "2023-02-02T17:27:29.896Z",
 * "LastName__c": "chakra",
 * "Account_ID__c": "0015000000SO8jRAAT",
 * "Contact_ID__c": "003R000001mCSRBIA4",
 * "Theatre__c": "APAC",
 * "FirstName__c": "KATE_DEAR"
 * },
 * "event": {
 * "replayId": 3933214
 * }
 * }
 */
public class Payload {

  private String Company_Organization_Name__c;
  private String Division_ID__c;
  private boolean Share_to_Wi__c;
  private String CreatedById;
  private String Email_Work__c;
  private String Partner_Level__c;
  private String CreatedDate;
  private String LastName__c;
  private String Account_ID__c;
  private String Contact_ID__c;
  private String Theatre__c;
  private String FirstName__c;

  public String getCompany_Organization_Name__c() {
    return Company_Organization_Name__c;
  }
  public void setCompany_Organization_Name__c(String company_Organization_Name__c) {
    Company_Organization_Name__c = company_Organization_Name__c;
  }
  public String getDivision_ID__c() {
    return Division_ID__c;
  }
  @Override
  public String toString() {
    return "Payload [Company_Organization_Name__c=" + Company_Organization_Name__c + ", Division_ID__c="
        + Division_ID__c + ", Share_to_Wi__c=" + Share_to_Wi__c + ", CreatedById=" + CreatedById + ", Email_Work__c="
        + Email_Work__c + ", Partner_Level__c=" + Partner_Level__c + ", CreatedDate=" + CreatedDate + ", LastName__c="
        + LastName__c + ", Account_ID__c=" + Account_ID__c + ", Contact_ID__c=" + Contact_ID__c + ", Theatre__c="
        + Theatre__c + ", FirstName__c=" + FirstName__c + "]";
  }
  public void setDivision_ID__c(String division_ID__c) {
    Division_ID__c = division_ID__c;
  }
  public boolean isShare_to_Wi__c() {
    return Share_to_Wi__c;
  }
  public void setShare_to_Wi__c(boolean share_to_Wi__c) {
    Share_to_Wi__c = share_to_Wi__c;
  }
  public String getCreatedById() {
    return CreatedById;
  }
  public void setCreatedById(String createdById) {
    CreatedById = createdById;
  }
  public String getEmail_Work__c() {
    return Email_Work__c;
  }
  public void setEmail_Work__c(String email_Work__c) {
    Email_Work__c = email_Work__c;
  }
  public String getPartner_Level__c() {
    return Partner_Level__c;
  }
  public void setPartner_Level__c(String partner_Level__c) {
    Partner_Level__c = partner_Level__c;
  }
  public String getCreatedDate() {
    return CreatedDate;
  }
  public void setCreatedDate(String createdDate) {
    CreatedDate = createdDate;
  }
  public String getLastName__c() {
    return LastName__c;
  }
  public void setLastName__c(String lastName__c) {
    LastName__c = lastName__c;
  }
  public String getAccount_ID__c() {
    return Account_ID__c;
  }
  public void setAccount_ID__c(String account_ID__c) {
    Account_ID__c = account_ID__c;
  }
  public String getContact_ID__c() {
    return Contact_ID__c;
  }
  public void setContact_ID__c(String contact_ID__c) {
    Contact_ID__c = contact_ID__c;
  }
  public String getTheatre__c() {
    return Theatre__c;
  }
  public void setTheatre__c(String theatre__c) {
    Theatre__c = theatre__c;
  }
  public String getFirstName__c() {
    return FirstName__c;
  }
  public void setFirstName__c(String firstName__c) {
    FirstName__c = firstName__c;
  }

  
}
