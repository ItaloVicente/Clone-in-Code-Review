
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.errors.TransportException;

public abstract class RemoteCommandConnection {
	final TransportGitSsh fTransport;

	public RemoteCommandConnection(TransportGitSsh transport) {
		fTransport = transport;
	}

	public abstract void exec(String commandName
			throws TransportException;

	public abstract void connect(URIish uri) throws TransportException;

	public abstract InputStream getInputStream() throws IOException;

	public abstract OutputStream getOutputStream() throws IOException;

	public abstract InputStream getErrorStream() throws IOException;

	public abstract int getExitStatus();

	public abstract void close();
}
