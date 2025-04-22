package org.eclipse.jgit.transport.sshd.agent;

import java.io.Closeable;
import java.io.IOException;

public interface Connector extends Closeable {

	boolean connect() throws IOException;

	byte[] rpc(byte command

	default byte[] rpc(byte command) throws IOException {
		return rpc(command
	}
}
