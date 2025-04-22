
package org.eclipse.jgit.internal.ketch;

import java.util.Date;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.ObjectId;

public class ReplicaSnapshot {
	final KetchReplica replica;
	ObjectId accepted;
	ObjectId committed;
	KetchReplica.State state;
	String error;
	long retryAtMillis;

	ReplicaSnapshot(KetchReplica replica) {
		this.replica = replica;
	}

	public KetchReplica getReplica() {
		return replica;
	}

	public KetchReplica.State getState() {
		return state;
	}

	@Nullable
	public ObjectId getAccepted() {
		return accepted;
	}

	@Nullable
	public ObjectId getCommitted() {
		return committed;
	}

	@Nullable
	public String getErrorMessage() {
		return error;
	}

	@Nullable
	public Date getRetryAt() {
		return retryAtMillis > 0 ? new Date(retryAtMillis) : null;
	}
}
