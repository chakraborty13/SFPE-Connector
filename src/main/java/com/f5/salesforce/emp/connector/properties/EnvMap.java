package com.f5.salesforce.emp.connector.properties;

import java.util.Map;

public class EnvMap {
  public void listEnv() {
    Map<String, String> env = System.getenv();
    for (String envName : env.keySet()) {
      System.out.format("%s=%s%n",
          envName,
          env.get(envName));
    }
  }

  public String getEnv(String key) {
    String value = null;
    Map<String, String> env = System.getenv();
    for (Map.Entry<String, String> entry : env.entrySet()) {
      //logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
      if (entry.getKey().equals(key)) {
        value = entry.getValue();
      }
    }
    return value;
  }
}

