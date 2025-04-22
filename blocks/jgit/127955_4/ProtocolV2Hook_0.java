package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.ObjectId;

class FetchV2Request {
	private final List<ObjectId> peerHas;

	private final TreeMap<String

	private final Set<ObjectId> wantsIds;

	private final Set<ObjectId> clientShallowCommits;

	private final int shallowSince;

	private final List<String> shallowExcludeRefs;

	private final int depth;

	private final long filterBlobLimit;

	private final Set<String> options;

	private FetchV2Request(List<ObjectId> peerHas
			TreeMap<String
			Set<ObjectId> clientShallowCommits
			List<String> shallowExcludeRefs
			Set<String> options) {
		this.peerHas = peerHas;
		this.wantedRefs = wantedRefs;
		this.wantsIds = wantsIds;
		this.clientShallowCommits = clientShallowCommits;
		this.shallowSince = shallowSince;
		this.shallowExcludeRefs = shallowExcludeRefs;
		this.depth = depth;
		this.filterBlobLimit = filterBlobLimit;
		this.options = options;
	}

	@NonNull
	List<ObjectId> getPeerHas() {
		return this.peerHas;
	}

	@NonNull
	Map<String
		return this.wantedRefs;
	}

	@NonNull
	Set<ObjectId> getWantsIds() {
		return wantsIds;
	}

	@NonNull
	Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	int getShallowSince() {
		return shallowSince;
	}

	@NonNull
	List<String> getShallowExcludeRefs() {
		return shallowExcludeRefs;
	}

	int getDepth() {
		return depth;
	}

	long getFilterBlobLimit() {
		return filterBlobLimit;
	}

	@NonNull
	Set<String> getOptions() {
		return options;
	}

	public static Builder builder() {
		return new Builder();
	}


	public static final class Builder {
		List<ObjectId> peerHas = new ArrayList<>();

		TreeMap<String

		Set<ObjectId> wantsIds = new HashSet<>();

		Set<ObjectId> clientShallowCommits = new HashSet<>();

		List<String> shallowExcludeRefs = new ArrayList<>();

		Set<String> options = new HashSet<>();

		int depth;

		int shallowSince;

		long filterBlobLimit = -1;

		private Builder() {
		}

		public Builder addPeerHas(ObjectId objectId) {
			peerHas.add(objectId);
			return this;
		}

		public Builder addWantedRef(String refName
			wantedRefs.put(refName
			return this;
		}

		public Builder addOption(String option) {
			options.add(option);
			return this;
		}

		public Builder addWantsIds(ObjectId objectId) {
			wantsIds.add(objectId);
			return this;
		}

		public Builder addClientShallowCommit(ObjectId shallowOid) {
			this.clientShallowCommits.add(shallowOid);
			return this;
		}

		public Builder setDepth(int d) {
			this.depth = d;
			return this;
		}

		public int getDepth() {
			return this.depth;
		}

		public boolean hasShallowExcludeRefs() {
			return shallowExcludeRefs.size() > 0;
		}

		public Builder addShallowExcludeRefs(String shallowExcludeRef) {
			this.shallowExcludeRefs.add(shallowExcludeRef);
			return this;
		}

		public Builder setShallowSince(int value) {
			this.shallowSince = value;
			return this;
		}

		public int getShallowSince() {
			return this.shallowSince;
		}

		public Builder setFilterBlobLimit(long filterBlobLimit) {
			this.filterBlobLimit = filterBlobLimit;
			return this;
		}

		public FetchV2Request build() {
			return new FetchV2Request(peerHas
					clientShallowCommits
					depth
		}
	}
}
