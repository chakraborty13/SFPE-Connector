package com.f5.salesforce.emp.connector.model;

public class Event {
  
  private int replayId;

    public Event(int replayId) {
    this.replayId = replayId;
  }

  public int getReplayId() {
    return replayId;
  }

  public void setReplayId(int replayId) {
    this.replayId = replayId;
  }


  
}
