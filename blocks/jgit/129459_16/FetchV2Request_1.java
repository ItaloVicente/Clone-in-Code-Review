package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.internal.transport.parser.FetchRequest;
import org.eclipse.jgit.lib.ObjectId;

final class FetchV0Request extends FetchRequest {

	FetchV0Request(Set<ObjectId> wantIds
			Set<ObjectId> clientShallowCommits
			Set<String> clientCapabilities) {
		super(wantIds
				clientCapabilities);
	}

	static final class Builder {

		int depth;

		Set<ObjectId> wantIds = new HashSet<>();

		Set<ObjectId> clientShallowCommits = new HashSet<>();

		long filterBlobLimit = -1;

		Set<String> clientCaps = new HashSet<>();

		Builder addWantId(ObjectId objectId) {
			wantIds.add(objectId);
			return this;
		}

		Builder setDepth(int d) {
			depth = d;
			return this;
		}

		Builder addClientShallowCommit(ObjectId shallowOid) {
			clientShallowCommits.add(shallowOid);
			return this;
		}

		Builder addAllClientCapabilities(Collection<String> clientCapabilities) {
			clientCaps.addAll(clientCapabilities);
			return this;
		}

		Builder setFilterBlobLimit(long filterBlobLim) {
			filterBlobLimit = filterBlobLim;
			return this;
		}

		FetchV0Request build() {
			return new FetchV0Request(wantIds
					filterBlobLimit
		}
	}
}
