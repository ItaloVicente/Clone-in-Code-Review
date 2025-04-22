package org.eclipse.jgit.internal.transport.sshd.proxy;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jgit.annotations.NonNull;

public class AuthenticationChallenge {

	private final String mechanism;

	private String token;

	private Map<String

	public AuthenticationChallenge(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getMechanism() {
		return mechanism;
	}

	public String getToken() {
		return token;
	}

	@NonNull
	public Map<String
		return arguments == null ? Collections.emptyMap() : arguments;
	}

	void addArgument(String key
		if (arguments == null) {
			arguments = new LinkedHashMap<>();
		}
		arguments.put(key
	}

	void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AuthenticationChallenge[" + mechanism + '
	}
}
