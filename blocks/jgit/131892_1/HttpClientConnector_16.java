package org.eclipse.jgit.transport.sshd.proxy;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.sshd.client.session.ClientSession;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.transport.sshd.JGitClientSession;

public abstract class AbstractClientProxyConnector
		implements StatefulProxyConnector {

	private static long DEFAULT_PROXY_TIMEOUT_MILLIS = TimeUnit.SECONDS
			.toMillis(30L);

	private Object lock = new Object();

	private boolean done;

	private Callable<Void> startSsh;

	private AtomicReference<Runnable> unregister = new AtomicReference<>();

	private long remainingProxyProtocolTime = DEFAULT_PROXY_TIMEOUT_MILLIS;

	private long lastProxyOperationTime = 0L;

	protected final InetSocketAddress remoteAddress;

	protected final InetSocketAddress proxyAddress;

	protected String proxyUser;

	protected char[] proxyPassword;

	public AbstractClientProxyConnector(@NonNull InetSocketAddress proxyAddress
			@NonNull InetSocketAddress remoteAddress
			char[] proxyPassword) {
		this.proxyAddress = proxyAddress;
		this.remoteAddress = remoteAddress;
		this.proxyUser = proxyUser;
		this.proxyPassword = proxyPassword == null ? new char[0]
				: proxyPassword;
	}

	protected void init(ClientSession session) {
		remainingProxyProtocolTime = session.getLongProperty(
				StatefulProxyConnector.TIMEOUT_PROPERTY
				DEFAULT_PROXY_TIMEOUT_MILLIS);
		if (remainingProxyProtocolTime <= 0L) {
			remainingProxyProtocolTime = DEFAULT_PROXY_TIMEOUT_MILLIS;
		}
		if (session instanceof JGitClientSession) {
			JGitClientSession s = (JGitClientSession) session;
			unregister.set(() -> s.setProxyHandler(null));
			s.setProxyHandler(this);
		} else {
			throw new IllegalStateException(
		}
	}

	protected long getTimeout() {
		long last = lastProxyOperationTime;
		long now = System.nanoTime();
		lastProxyOperationTime = now;
		long remaining = remainingProxyProtocolTime;
		if (last != 0L) {
			long elapsed = now - last;
			remaining -= elapsed;
			if (remaining < 0L) {
			}
		}
		remainingProxyProtocolTime = remaining;
		return remaining;
	}

	protected void adjustTimeout() {
		lastProxyOperationTime = System.nanoTime();
	}

	protected void setDone(boolean success) throws Exception {
		Callable<Void> starter;
		Runnable unset = unregister.getAndSet(null);
		if (unset != null) {
			unset.run();
		}
		synchronized (lock) {
			done = true;
			starter = startSsh;
			startSsh = null;
		}
		if (success && starter != null) {
			starter.call();
		}
	}

	@Override
	public void runWhenDone(Callable<Void> starter) throws Exception {
		synchronized (lock) {
			if (!done) {
				this.startSsh = starter;
				return;
			}
		}
		starter.call();
	}

	protected void clearPassword() {
		Arrays.fill(proxyPassword
		proxyPassword = new char[0];
	}
}
