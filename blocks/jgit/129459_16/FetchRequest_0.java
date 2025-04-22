package org.eclipse.jgit.internal.transport.parser;

import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

public abstract class FetchRequest {

	final Set<ObjectId> wantIds;

	final int depth;

	final Set<ObjectId> clientShallowCommits;

	final long filterBlobLimit;

	final Set<String> clientCapabilities;

	public FetchRequest(Set<ObjectId> wantIds
			Set<ObjectId> clientShallowCommits
			Set<String> clientCapabilities) {
		this.wantIds = wantIds;
		this.depth = depth;
		this.clientShallowCommits = clientShallowCommits;
		this.filterBlobLimit = filterBlobLimit;
		this.clientCapabilities = clientCapabilities;
	}

	public Set<ObjectId> getWantIds() {
		return wantIds;
	}

	public int getDepth() {
		return depth;
	}

	public Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	public long getFilterBlobLimit() {
		return filterBlobLimit;
	}

	public Set<String> getClientCapabilities() {
		return clientCapabilities;
	}
}
