package net.spy.memcached.vbucket.config;

import java.util.List;


public class ConfigDifference {

    private List<String> serversAdded;

    private List<String> serversRemoved;

    private int vbucketsChanges;

    private boolean sequenceChanged;

    public List<String> getServersAdded() {
        return serversAdded;
    }

    public void setServersAdded(List<String> serversAdded) {
        this.serversAdded = serversAdded;
    }

    public List<String> getServersRemoved() {
        return serversRemoved;
    }

    public void setServersRemoved(List<String> serversRemoved) {
        this.serversRemoved = serversRemoved;
    }

    public int getVbucketsChanges() {
        return vbucketsChanges;
    }

    public void setVbucketsChanges(int vbucketsChanges) {
        this.vbucketsChanges = vbucketsChanges;
    }

    public boolean isSequenceChanged() {
        return sequenceChanged;
    }

    public void setSequenceChanged(boolean sequenceChanged) {
        this.sequenceChanged = sequenceChanged;
    }
}
