package org.eclipse.jgit.transport.sshd;

import java.net.InetSocketAddress;
import java.security.PublicKey;
import java.util.List;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.transport.CredentialsProvider;

public interface ServerKeyDatabase {

	@NonNull
	List<PublicKey> lookup(@NonNull String connectAddress
			@NonNull InetSocketAddress remoteAddress
			@NonNull Configuration config);

	boolean accept(@NonNull String connectAddress
			@NonNull InetSocketAddress remoteAddress
			@NonNull PublicKey serverKey
			@NonNull Configuration config

	interface Configuration {

		List<String> getUserKnownHostsFiles();

		List<String> getGlobalKnownHostsFiles();

		enum StrictHostKeyChecking {
			ASK
			DENY
			ALLOW
			ACCEPT_NEW
		}

		@NonNull
		StrictHostKeyChecking getStrictHostKeyChecking();

		@NonNull
		String getUsername();
	}
}
