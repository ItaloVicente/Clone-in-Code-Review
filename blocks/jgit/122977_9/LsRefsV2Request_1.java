package org.eclipse.jgit.transport;

public final class CapabilitiesV2Request {
	private CapabilitiesV2Request() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Builder() {
		}

		public CapabilitiesV2Request build() {
			return new CapabilitiesV2Request();
		}
	}
}
