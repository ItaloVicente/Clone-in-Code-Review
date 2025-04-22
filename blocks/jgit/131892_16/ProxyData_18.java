package org.eclipse.jgit.transport.sshd;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

import org.eclipse.jgit.annotations.NonNull;

public class ProxyData {

	private final @NonNull Proxy proxy;

	private final String proxyUser;

	private final char[] proxyPassword;

	public ProxyData(@NonNull Proxy proxy) {
		this(proxy
	}

	public ProxyData(@NonNull Proxy proxy
			char[] proxyPassword) {
		this.proxy = proxy;
		if (!(proxy.address() instanceof InetSocketAddress)) {
			throw new IllegalArgumentException(
		}
		this.proxyUser = proxyUser;
		this.proxyPassword = proxyPassword == null ? null
				: proxyPassword.clone();
	}

	@NonNull
	public Proxy getProxy() {
		return proxy;
	}

	public String getUser() {
		return proxyUser;
	}

	public char[] getPassword() {
		return proxyPassword == null ? null : proxyPassword.clone();
	}

	public void clearPassword() {
		if (proxyPassword != null) {
			Arrays.fill(proxyPassword
		}
	}

}
