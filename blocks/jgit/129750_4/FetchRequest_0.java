package org.eclipse.jgit.transport;

import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

abstract class FetchRequest {

	final Set<ObjectId> wantIds;

	final int depth;

	final Set<ObjectId> clientShallowCommits;

	final long filterBlobLimit;

	final Set<String> clientCapabilities;

	FetchRequest(Set<ObjectId> wantIds
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
}
