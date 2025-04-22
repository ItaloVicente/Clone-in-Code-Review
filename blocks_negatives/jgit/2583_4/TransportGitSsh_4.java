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
				throw new TransportException(uri, je.getMessage(), je);
			}
		}

		@Override
		void connect() throws TransportException {
			try {
				channel.connect(getTimeout() > 0 ? getTimeout() * 1000 : 0);
				if (!channel.isConnected())
					throw new TransportException(uri, "connection failed");
			} catch (JSchException e) {
				throw new TransportException(uri, e.getMessage(), e);
			}
		}

		@Override
		InputStream getInputStream() throws IOException {
			return channel.getInputStream();
		}

		@Override
		OutputStream getOutputStream() throws IOException {
			final OutputStream out = channel.getOutputStream();
			if (getTimeout() <= 0)
				return out;
			final PipedInputStream pipeIn = new PipedInputStream();
			final StreamCopyThread copier = new StreamCopyThread(pipeIn, out);
			final PipedOutputStream pipeOut = new PipedOutputStream(pipeIn) {
				@Override
				public void flush() throws IOException {
					super.flush();
					copier.flush();
				}

				@Override
				public void close() throws IOException {
					super.close();
					try {
						copier.join(getTimeout() * 1000);
					} catch (InterruptedException e) {
					}
				}
			};
			copier.start();
			return pipeOut;
		}

		@Override
		InputStream getErrorStream() throws IOException {
			return channel.getErrStream();
		}

		@Override
		int getExitStatus() {
			return exitStatus;
		}

		@Override
		void close() {
			if (channel != null) {
				try {
					exitStatus = channel.getExitStatus();
					if (channel.isConnected())
						channel.disconnect();
				} finally {
					channel = null;
				}
			}
		}
	}

	private static boolean useExtConnection() {
