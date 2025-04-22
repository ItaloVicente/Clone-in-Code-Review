package org.eclipse.jgit.internal.transport.sshd;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.sshd.KeyPasswordProvider;

public class PasswordProviderWrapper implements RepeatingFilePasswordProvider {

	private final KeyPasswordProvider delegate;

	private Map<String

	public PasswordProviderWrapper(@NonNull KeyPasswordProvider delegate) {
		this.delegate = delegate;
	}

	@Override
	public void setAttempts(int numberOfPasswordPrompts) {
		delegate.setAttempts(numberOfPasswordPrompts);
	}

	@Override
	public int getAttempts() {
		return delegate.getAttempts();
	}

	@Override
	public String getPassword(String resourceKey) throws IOException {
		int attempt = counts
				.computeIfAbsent(resourceKey
		char[] passphrase = delegate.getPassphrase(toUri(resourceKey)
		if (passphrase == null) {
			return null;
		}
		try {
			return new String(passphrase);
		} finally {
			Arrays.fill(passphrase
		}
	}

	@Override
	public ResourceDecodeResult handleDecodeAttemptResult(String resourceKey
			String password
			throws IOException
		AtomicInteger count = counts.get(resourceKey);
		int numberOfAttempts = count == null ? 0 : count.incrementAndGet();
		try {
			if (delegate.keyLoaded(toUri(resourceKey)
				return ResourceDecodeResult.RETRY;
			} else {
				counts.remove(resourceKey);
				return ResourceDecodeResult.TERMINATE;
			}
		} catch (RuntimeException e) {
			counts.remove(resourceKey);
			throw e;
		}
	}

	private URIish toUri(String resourceKey) {
		try {
			return new URIish(resourceKey);
		} catch (URISyntaxException e) {
		}
	}

}
