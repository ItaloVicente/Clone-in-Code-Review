package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.io.IOException;

import org.eclipse.jgit.transport.sshd.agent.AbstractConnector;

public class PageantConnector extends AbstractConnector {

	private final WindowsLibrary lib;

	public PageantConnector() {
		this.lib = new WindowsLibrary();
	}

	@Override
	public boolean connect() throws IOException {
		return lib.isPageantAvailable();
	}

	@Override
	public void disconnect() throws IOException {
	}

	@Override
	public byte[] rpc(byte command
		try (WindowsLibrary.Pipe pipe = lib
				.createPipe(getClass().getSimpleName()
						getMaximumMessageLength())) {
			prepareMessage(command
			pipe.send(message);
			byte[] lengthBuf = new byte[4];
			pipe.receive(lengthBuf);
			int length = toLength(command
			byte[] payload = new byte[length];
			pipe.receive(payload);
			return payload;
		}
	}
}
