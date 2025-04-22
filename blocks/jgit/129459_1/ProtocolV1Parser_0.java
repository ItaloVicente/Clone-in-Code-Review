package org.eclipse.jgit.transport;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

final class FetchV1Request {

	final Set<ObjectId> wantsIds;

	final int depth;

	final Set<ObjectId> clientShallowCommits;

	final long filterBlobLimit;

	final Set<String> options;

	FetchV1Request(Set<ObjectId> wantsIds
			Set<ObjectId> clientShallowCommits
			Set<String> options) {

		this.wantsIds = wantsIds;
		this.depth = depth;
		this.clientShallowCommits = clientShallowCommits;
		this.filterBlobLimit = filterBlobLimit;
		this.options = options;
	}

	Set<ObjectId> getWantsIds() {
		return wantsIds;
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

	Set<String> getOptions() {
		return options;
	}

	static final class Builder {

		int depth;

		Set<ObjectId> wantsIds = new HashSet<>();

		Set<ObjectId> clientShallowCommits = new HashSet<>();

		long filterBlobLimit = -1;

		Set<String> options = new HashSet<>();

		Builder addWantsIds(ObjectId objectId) {
			this.wantsIds.add(objectId);
			return this;
		}

		Builder setDepth(int d) {
			this.depth = d;
			return this;
		}

		Builder addClientShallowCommit(ObjectId shallowOid) {
			this.clientShallowCommits.add(shallowOid);
			return this;
		}

		Builder addAllOptions(Set<String> opts) {
			this.options.addAll(opts);
			return this;
		}

		Builder setFilterBlobLimit(long filterBlobLimit) {
			this.filterBlobLimit = filterBlobLimit;
			return this;
		}

		FetchV1Request build() {
			return new FetchV1Request(wantsIds
					filterBlobLimit
		}
	}
}
