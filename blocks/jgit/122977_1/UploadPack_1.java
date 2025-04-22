package org.eclipse.jgit.transport;

public interface ProtocolV2Hook {
	default void onCapabilities() throws ServiceMayNotContinueException {
	}

	default void onLsRefs(LsRefsV2Request req)
			throws ServiceMayNotContinueException {
	}
}
