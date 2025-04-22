	private void connect(ChannelExec channel) throws TransportException {
		try {
			channel.connect(getTimeout() > 0 ? getTimeout() * 1000 : 0);
			if (!channel.isConnected())
				throw new TransportException(uri
		} catch (JSchException e) {
			throw new TransportException(uri
		}
	}

