package org.eclipse.jgit.transport.sshd.agent;

import java.io.IOException;

public interface Connector {

	boolean connect() throws IOException;

	void disconnect() throws IOException;

	byte[] rpc(byte command

	default byte[] rpc(byte command) throws IOException {
		return rpc(command
	}
}
