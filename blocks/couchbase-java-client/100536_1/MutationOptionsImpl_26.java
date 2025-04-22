
package com.couchbase.client.java.internal.options;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;
import com.couchbase.client.java.options.MutationOptions;

public class MutationOptionsImpl extends MutationOptions {

	private final int expiry;
	private final ReplicateTo replicaMode;
	private final PersistTo persistTo;

	protected MutationOptionsImpl(int expiry, ReplicateTo replicateTo, PersistTo persistTo) {
		this.expiry = expiry;
		this.replicaMode = replicateTo;
		this.persistTo = persistTo;
	}

	@Override
	public int expiry() {
		return 0;
	}

	@Override
	public ReplicateTo replicateTo() {
		return null;
	}

	@Override
	public PersistTo persistTo() {
		return null;
	}
}
