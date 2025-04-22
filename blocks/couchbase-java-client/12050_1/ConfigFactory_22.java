
package com.couchbase.client.vbucket.config;

import java.util.List;

public class ConfigDifference {

  private List<String> serversAdded;

  private List<String> serversRemoved;

  private int vbucketsChanges;

  private boolean sequenceChanged;

  public List<String> getServersAdded() {
    return serversAdded;
  }

  protected void setServersAdded(List<String> newServersAdded) {
    this.serversAdded = newServersAdded;
  }

  public List<String> getServersRemoved() {
    return serversRemoved;
  }

  protected void setServersRemoved(List<String> newServersRemoved) {
    this.serversRemoved = newServersRemoved;
  }

  public int getVbucketsChanges() {
    return vbucketsChanges;
  }

  protected void setVbucketsChanges(int newVbucketsChanges) {
    this.vbucketsChanges = newVbucketsChanges;
  }

  public boolean isSequenceChanged() {
    return sequenceChanged;
  }

  protected void setSequenceChanged(boolean newSequenceChanged) {
    this.sequenceChanged = newSequenceChanged;
  }
}
