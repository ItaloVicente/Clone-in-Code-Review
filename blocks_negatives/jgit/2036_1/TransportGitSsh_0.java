	ChannelExec exec(final String exe) throws TransportException {
		initSession();

		try {
			final ChannelExec channel = (ChannelExec) sock.openChannel("exec");
			channel.setCommand(commandFor(exe));
			return channel;
		} catch (JSchException je) {
			throw new TransportException(uri, je.getMessage(), je);
		}
	}

	private void connect(ChannelExec channel) throws TransportException {
		try {
			channel.connect(getTimeout() > 0 ? getTimeout() * 1000 : 0);
			if (!channel.isConnected())
				throw new TransportException(uri, "connection failed");
		} catch (JSchException e) {
			throw new TransportException(uri, e.getMessage(), e);
		}
	}

