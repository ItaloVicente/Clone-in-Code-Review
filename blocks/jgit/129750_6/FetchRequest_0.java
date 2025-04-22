package org.eclipse.jgit.transport;

import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;
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
		if (wantIds == null || clientShallowCommits == null
				|| clientCapabilities == null) {
			throw new NullPointerException();
		}

		this.wantIds = wantIds;
		this.depth = depth;
		this.clientShallowCommits = clientShallowCommits;
		this.filterBlobLimit = filterBlobLimit;
		this.clientCapabilities = clientCapabilities;
	}

	@NonNull
	Set<ObjectId> getWantIds() {
		return wantIds;
	}

	int getDepth() {
		return depth;
	}

	@NonNull
	Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	long getFilterBlobLimit() {
		return filterBlobLimit;
	}

	@NonNull
	Set<String> getClientCapabilities() {
		return clientCapabilities;
	}
}
