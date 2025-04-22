	private OutputStream outputStream(ChannelExec channel) throws IOException {
		final OutputStream out = channel.getOutputStream();
		if (getTimeout() <= 0)
			return out;
		final PipedInputStream pipeIn = new PipedInputStream();
		final StreamCopyThread copyThread = new StreamCopyThread(pipeIn, out);
		final PipedOutputStream pipeOut = new PipedOutputStream(pipeIn) {
			@Override
			public void flush() throws IOException {
				super.flush();
				copyThread.flush();
