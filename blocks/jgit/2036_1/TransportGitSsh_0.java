	private abstract class Connection {
		abstract void exec(String commandName) throws TransportException;

		abstract void connect() throws TransportException;

		abstract InputStream getInputStream() throws IOException;

		abstract OutputStream getOutputStream() throws IOException;

		abstract InputStream getErrorStream() throws IOException;

		abstract int getExitStatus();

		abstract void close();
	}

	private class JschConnection extends Connection {
		private ChannelExec channel;

		private int exitStatus;

		@Override
		void exec(String commandName) throws TransportException {
			initSession();
			try {
				channel = (ChannelExec) sock.openChannel("exec");
				channel.setCommand(commandFor(commandName));
			} catch (JSchException je) {
				throw new TransportException(uri
			}
		}

		@Override
		void connect() throws TransportException {
			try {
				channel.connect(getTimeout() > 0 ? getTimeout() * 1000 : 0);
				if (!channel.isConnected())
					throw new TransportException(uri
			} catch (JSchException e) {
				throw new TransportException(uri
