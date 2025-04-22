
package org.eclipse.jgit.internal.ketch;

import org.eclipse.jgit.lib.ObjectId;

public class ReplicaSnapshot {
	String name;
	KetchReplica.Participation type;
	ObjectId txnAccepted;
	ObjectId txnCommitted;
	KetchReplica.State state;
	String error;
	long retryAtMillis;

	ReplicaSnapshot() {
	}
}
