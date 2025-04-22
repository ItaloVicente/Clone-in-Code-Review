package org.eclipse.jgit.transport;

import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

abstract class FetchRequest {

	final Set<ObjectId> wantsIds;

	final int depth;

	final Set<ObjectId> clientShallowCommits;

	final long filterBlobLimit;

	final Set<String> options;

	FetchRequest(Set<ObjectId> wantsIds
			Set<ObjectId> clientShallowCommits
			Set<String> options) {
		this.wantsIds = wantsIds;
		this.depth = depth;
		this.clientShallowCommits = clientShallowCommits;
		this.filterBlobLimit = filterBlobLimit;
		this.options = options;
	}

	protected Set<ObjectId> getWantsIds() {
		return wantsIds;
	}

	protected int getDepth() {
		return depth;
	}

	protected Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	protected long getFilterBlobLimit() {
		return filterBlobLimit;
	}

	protected Set<String> getOptions() {
		return options;
	}

}
