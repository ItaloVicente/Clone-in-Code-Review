package org.eclipse.jgit.transport;

public interface ProtocolV2Hook {
	static ProtocolV2Hook DEFAULT = new ProtocolV2Hook() {
	};

	default void onCapabilities(CapabilitiesV2Request req)
			throws ServiceMayNotContinueException {
	}

	default void onLsRefs(LsRefsV2Request req)
			throws ServiceMayNotContinueException {
	}

	default void onFetch(FetchV2Request req)
			throws ServiceMayNotContinueException {
	}
}
