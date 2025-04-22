package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

final class FetchV0Request {

	final Set<ObjectId> wantIds;

	final int depth;

	final Set<ObjectId> clientShallowCommits;

	final long filterBlobLimit;

	final Set<String> clientCapabilities;

	FetchV0Request(Set<ObjectId> wantIds
			Set<ObjectId> clientShallowCommits
			Set<String> clientCapabilities) {
		this.wantIds = wantIds;
		this.depth = depth;
		this.clientShallowCommits = clientShallowCommits;
		this.filterBlobLimit = filterBlobLimit;
		this.clientCapabilities = clientCapabilities;
	}

	Set<ObjectId> getWantIds() {
		return wantIds;
	}

	int getDepth() {
		return depth;
	}

	Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	long getFilterBlobLimit() {
		return filterBlobLimit;
	}

	Set<String> getClientCapabilities() {
		return clientCapabilities;
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

		Builder addClientCapabilities(Collection<String> clientCapabilities) {
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
