package org.eclipse.jgit.transport;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.ObjectId;

public final class FetchV2Request extends FetchRequest {
	private final List<ObjectId> peerHas;

	private final List<String> wantedRefs;

	private final boolean doneReceived;

	@NonNull
	private final List<String> serverOptions;

	FetchV2Request(@NonNull List<ObjectId> peerHas
			@NonNull List<String> wantedRefs
			@NonNull Set<ObjectId> wantIds
			@NonNull Set<ObjectId> clientShallowCommits
			@NonNull List<String> deepenNotRefs
			@NonNull FilterSpec filterSpec
			boolean doneReceived
			@Nullable String agent
		super(wantIds
				clientCapabilities
				deepenNotRefs
		this.peerHas = requireNonNull(peerHas);
		this.wantedRefs = requireNonNull(wantedRefs);
		this.doneReceived = doneReceived;
		this.serverOptions = requireNonNull(serverOptions);
	}

	@NonNull
	List<ObjectId> getPeerHas() {
		return peerHas;
	}

	@NonNull
	public List<String> getWantedRefs() {
		return wantedRefs;
	}

	boolean wasDoneReceived() {
		return doneReceived;
	}

	@NonNull
	public List<String> getServerOptions() {
		return serverOptions;
	}

	static Builder builder() {
		return new Builder();
	}

	static final class Builder {
		final List<ObjectId> peerHas = new ArrayList<>();

		final List<String> wantedRefs = new ArrayList<>();

		final Set<ObjectId> wantIds = new HashSet<>();

		final Set<ObjectId> clientShallowCommits = new HashSet<>();

		final List<String> deepenNotRefs = new ArrayList<>();

		final Set<String> clientCapabilities = new HashSet<>();

		int depth;

		int deepenSince;

		FilterSpec filterSpec = FilterSpec.NO_FILTER;

		boolean doneReceived;

		@Nullable
		String agent;

		final List<String> serverOptions = new ArrayList<>();

		private Builder() {
		}

		Builder addPeerHas(ObjectId objectId) {
			peerHas.add(objectId);
			return this;
		}

		Builder addWantedRef(String refName) {
			wantedRefs.add(refName);
			return this;
		}

		Builder addClientCapability(String clientCapability) {
			clientCapabilities.add(clientCapability);
			return this;
		}

		Builder addWantId(ObjectId wantId) {
			wantIds.add(wantId);
			return this;
		}

		Builder addClientShallowCommit(ObjectId shallowOid) {
			clientShallowCommits.add(shallowOid);
			return this;
		}

		Builder setDepth(int d) {
			depth = d;
			return this;
		}

		int getDepth() {
			return depth;
		}

		boolean hasDeepenNotRefs() {
			return !deepenNotRefs.isEmpty();
		}

		Builder addDeepenNotRef(String deepenNotRef) {
			deepenNotRefs.add(deepenNotRef);
			return this;
		}

		Builder setDeepenSince(int value) {
			deepenSince = value;
			return this;
		}

		int getDeepenSince() {
			return deepenSince;
		}

		Builder setFilterSpec(@NonNull FilterSpec filter) {
			filterSpec = requireNonNull(filter);
			return this;
		}

		Builder setDoneReceived() {
			doneReceived = true;
			return this;
		}

		Builder setAgent(@Nullable String agentValue) {
			agent = agentValue;
			return this;
		}

		Builder addServerOption(@NonNull String value) {
			serverOptions.add(value);
			return this;
		}

		FetchV2Request build() {
			return new FetchV2Request(peerHas
					clientShallowCommits
					depth
					agent
		}
	}
}
