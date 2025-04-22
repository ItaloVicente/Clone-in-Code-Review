	ChannelSftp newSftp() throws TransportException {
		final int tms = getTimeout() > 0 ? getTimeout() * 1000 : 0;
		try {
			final Channel channel = ((JschSession) getSession())
					.getSftpChannel();
			channel.connect(tms);
			return (ChannelSftp) channel;
		} catch (JSchException je) {
			throw new TransportException(uri, je.getMessage(), je);
		}
