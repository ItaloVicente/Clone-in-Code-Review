package org.eclipse.jgit.transport;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;

public final class LsRefsV2Request {
	private final List<String> refPrefixes;

	private final boolean symrefs;

	private final boolean peel;

	@Nullable
	private final String agent;

	@NonNull
	private final List<String> serverOptions;

	private LsRefsV2Request(List<String> refPrefixes
			boolean peel
			@NonNull List<String> serverOptions) {
		this.refPrefixes = refPrefixes;
		this.symrefs = symrefs;
		this.peel = peel;
		this.agent = agent;
		this.serverOptions = requireNonNull(serverOptions);
	}

	public List<String> getRefPrefixes() {
		return refPrefixes;
	}

	public boolean getSymrefs() {
		return symrefs;
	}

	public boolean getPeel() {
		return peel;
	}

	@Nullable
	public String getAgent() {
		return agent;
	}

	@NonNull
	public List<String> getServerOptions() {
		return serverOptions;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<String> refPrefixes = Collections.emptyList();

		private boolean symrefs;

		private boolean peel;

		private final List<String> serverOptions = new ArrayList<>();

		private String agent;

		private Builder() {
		}

		public Builder setRefPrefixes(List<String> value) {
			refPrefixes = value;
			return this;
		}

		public Builder setSymrefs(boolean value) {
			symrefs = value;
			return this;
		}

		public Builder setPeel(boolean value) {
			peel = value;
			return this;
		}

		public Builder addServerOption(@NonNull String value) {
			serverOptions.add(value);
			return this;
		}

		public Builder setAgent(@Nullable String value) {
			agent = value;
			return this;
		}

		public LsRefsV2Request build() {
			return new LsRefsV2Request(
					Collections.unmodifiableList(refPrefixes)
					agent
		}
	}
}
