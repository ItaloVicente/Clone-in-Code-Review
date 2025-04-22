
package org.eclipse.jgit.transport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class DaemonClient {
	private final Daemon daemon;

	private InetAddress peer;

	private InputStream rawIn;

	private OutputStream rawOut;

	DaemonClient(Daemon d) {
		daemon = d;
	}

	void setRemoteAddress(InetAddress ia) {
		peer = ia;
	}

	public Daemon getDaemon() {
		return daemon;
	}

	public InetAddress getRemoteAddress() {
		return peer;
	}

	public InputStream getInputStream() {
		return rawIn;
	}

	public OutputStream getOutputStream() {
		return rawOut;
	}

	void execute(Socket sock) throws IOException
			ServiceNotEnabledException
		rawIn = new BufferedInputStream(sock.getInputStream());
		rawOut = new BufferedOutputStream(sock.getOutputStream());

		if (0 < daemon.getTimeout())
			sock.setSoTimeout(daemon.getTimeout() * 1000);
		String cmd = new PacketLineIn(rawIn).readStringRaw();

		Collection<String> extraParameters = null;

		if (nulnul != -1) {
		}

		final int nul = cmd.indexOf('\0');
		if (nul >= 0) {
			cmd = cmd.substring(0
		}

		final DaemonService srv = getDaemon().matchService(cmd);
		if (srv == null)
			return;
		sock.setSoTimeout(0);
		srv.execute(this
	}
}
