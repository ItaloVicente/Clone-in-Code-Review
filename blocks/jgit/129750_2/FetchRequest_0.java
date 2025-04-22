package org.eclipse.jgit.internal.transport.parser;

import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

public abstract class FetchRequest {

	final Set<ObjectId> wantIds;

	final int depth;

	final Set<ObjectId> clientShallowCommits;

	final long filterBlobLimit;

	final Set<String> options;

	public FetchRequest(Set<ObjectId> wantIds
			Set<ObjectId> clientShallowCommits
			Set<String> options) {
		this.wantIds = wantIds;
		this.depth = depth;
		this.clientShallowCommits = clientShallowCommits;
		this.filterBlobLimit = filterBlobLimit;
		this.options = options;
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

	public Set<String> getOptions() {
		return options;
	}
}
