
package org.eclipse.jgit.transport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;

class TransportGitAnon extends TcpTransport implements PackTransport {
	static final int GIT_PORT = Daemon.DEFAULT_PORT;

	static final TransportProtocol PROTO_GIT = new TransportProtocol() {
		@Override
		public String getName() {
			return JGitText.get().transportProtoGitAnon;
		}

		@Override
		public Set<String> getSchemes() {
		}

		@Override
		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.HOST
					URIishField.PATH));
		}

		@Override
		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.PORT));
		}

		@Override
		public int getDefaultPort() {
			return GIT_PORT;
		}

		@Override
		public Transport open(URIish uri
				throws NotSupportedException {
			return new TransportGitAnon(local
		}

		@Override
		public Transport open(URIish uri) throws NotSupportedException
			return new TransportGitAnon(uri);
		}
	};

	TransportGitAnon(Repository local
		super(local
	}

	TransportGitAnon(URIish uri) {
		super(uri);
	}

	@Override
	public FetchConnection openFetch() throws TransportException {
		return new TcpFetchConnection();
	}

	@Override
	public PushConnection openPush() throws TransportException {
		return new TcpPushConnection();
	}

	@Override
	public void close() {
	}

	Socket openConnection() throws TransportException {
		final int tms = getTimeout() > 0 ? getTimeout() * 1000 : 0;
		final int port = uri.getPort() > 0 ? uri.getPort() : GIT_PORT;
		final Socket s = new Socket();
		try {
			final InetAddress host = InetAddress.getByName(uri.getHost());
			s.bind(null);
			s.connect(new InetSocketAddress(host
		} catch (IOException c) {
			try {
				s.close();
			} catch (IOException closeErr) {
			}
			if (c instanceof UnknownHostException)
				throw new TransportException(uri
			if (c instanceof ConnectException)
				throw new TransportException(uri
			throw new TransportException(uri
		}
		return s;
	}

	void service(String name
			throws IOException {
		final StringBuilder cmd = new StringBuilder();
		cmd.append(name);
		cmd.append(' ');
		cmd.append(uri.getPath());
		cmd.append('\0');
		cmd.append(uri.getHost());
		if (uri.getPort() > 0 && uri.getPort() != GIT_PORT) {
			cmd.append(uri.getPort());
		}
		cmd.append('\0');
		pckOut.writeString(cmd.toString());
		pckOut.flush();
	}

	class TcpFetchConnection extends BasePackFetchConnection {
		private Socket sock;

		TcpFetchConnection() throws TransportException {
			super(TransportGitAnon.this);
			sock = openConnection();
			try {
				InputStream sIn = sock.getInputStream();
				OutputStream sOut = sock.getOutputStream();

				sIn = new BufferedInputStream(sIn);
				sOut = new BufferedOutputStream(sOut);

				init(sIn
				service("git-upload-pack"
			} catch (IOException err) {
				close();
				throw new TransportException(uri
						JGitText.get().remoteHungUpUnexpectedly
			}
			readAdvertisedRefs();
		}

		@Override
		public void close() {
			super.close();

			if (sock != null) {
				try {
					sock.close();
				} catch (IOException err) {
				} finally {
					sock = null;
				}
			}
		}
	}

	class TcpPushConnection extends BasePackPushConnection {
		private Socket sock;

		TcpPushConnection() throws TransportException {
			super(TransportGitAnon.this);
			sock = openConnection();
			try {
				InputStream sIn = sock.getInputStream();
				OutputStream sOut = sock.getOutputStream();

				sIn = new BufferedInputStream(sIn);
				sOut = new BufferedOutputStream(sOut);

				init(sIn
				service("git-receive-pack"
			} catch (IOException err) {
				close();
				throw new TransportException(uri
						JGitText.get().remoteHungUpUnexpectedly
			}
			readAdvertisedRefs();
		}

		@Override
		public void close() {
			super.close();

			if (sock != null) {
				try {
					sock.close();
				} catch (IOException err) {
				} finally {
					sock = null;
				}
			}
		}
	}
}
