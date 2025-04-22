package org.eclipse.jgit.internal.transport.sshd;

import java.util.List;

import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.AttributeRepository.AttributeKey;

public interface GssApiWithMicAuthenticationReporter {

	static final AttributeKey<GssApiWithMicAuthenticationReporter> GSS_AUTHENTICATION_REPORTER = new AttributeKey<>();

	default void signalAuthenticationAttempt(ClientSession session
			String service
	}

	default void signalAuthenticationExhausted(ClientSession session
			String service) {
	}

	default void signalAuthenticationSuccess(ClientSession session
			String service
	}

	default void signalAuthenticationFailure(ClientSession session
			String service
			List<String> serverMethods) {
	}
}
