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

	protected void setServersAdded(List<String> serversAdded) {
		this.serversAdded = serversAdded;
	}

	public List<String> getServersRemoved() {
		return serversRemoved;
	}

	protected void setServersRemoved(List<String> serversRemoved) {
		this.serversRemoved = serversRemoved;
	}

	public int getVbucketsChanges() {
		return vbucketsChanges;
	}

	protected void setVbucketsChanges(int vbucketsChanges) {
		this.vbucketsChanges = vbucketsChanges;
	}

	public boolean isSequenceChanged() {
		return sequenceChanged;
	}

	protected void setSequenceChanged(boolean sequenceChanged) {
		this.sequenceChanged = sequenceChanged;
	}
}
