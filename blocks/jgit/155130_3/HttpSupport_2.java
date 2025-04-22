package org.eclipse.jgit.transport.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public abstract class DelegatingSSLSocketFactory extends SSLSocketFactory {

	private final SSLSocketFactory delegate;

	public DelegatingSSLSocketFactory(SSLSocketFactory delegate) {
		this.delegate = delegate;
	}

	@Override
	public SSLSocket createSocket() throws IOException {
		return prepare(delegate.createSocket());
	}

	@Override
	public SSLSocket createSocket(String host
		return prepare(delegate.createSocket(host
	}

	@Override
	public SSLSocket createSocket(String host
			InetAddress localAddress
		return prepare(
				delegate.createSocket(host
	}

	@Override
	public SSLSocket createSocket(InetAddress host
			throws IOException {
		return prepare(delegate.createSocket(host
	}

	@Override
	public SSLSocket createSocket(InetAddress host
			InetAddress localAddress
		return prepare(
				delegate.createSocket(host
	}

	@Override
	public SSLSocket createSocket(Socket socket
			boolean autoClose) throws IOException {
		return prepare(delegate.createSocket(socket
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return delegate.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return delegate.getSupportedCipherSuites();
	}

	private SSLSocket prepare(Socket socket) throws IOException {
		SSLSocket sslSocket = (SSLSocket) socket;
		configure(sslSocket);
		return sslSocket;
	}

	protected abstract void configure(SSLSocket socket) throws IOException;

}
