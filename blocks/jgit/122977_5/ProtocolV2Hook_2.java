package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.List;

public final class LsRefsV2Request {
	private final List<String> refPrefixes;

	private final boolean symrefs;

	private final boolean peel;

	private LsRefsV2Request(List<String> refPrefixes
			boolean peel) {
		this.refPrefixes = refPrefixes;
		this.symrefs = symrefs;
		this.peel = peel;
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

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<String> refPrefixes = Collections.emptyList();

		private boolean symrefs;

		private boolean peel;

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

		public LsRefsV2Request build() {
			return new LsRefsV2Request(
					Collections.unmodifiableList(refPrefixes)
		}
	}
}
