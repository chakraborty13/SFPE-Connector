package com.f5.salesforce.emp.connector.properties;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationProperties {

  public Properties properties;

  public ApplicationProperties() {
    properties = new Properties();
    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

    } catch (IOException ioex) {
      Logger.getLogger(getClass().getName()).log(Level.ALL,
          "IOException Occured while loading properties file::::" + ioex.getMessage());
    }
  }
}