		@Override
		protected KnownHostEntry prepareKnownHostEntry(
				ClientSession clientSession, SocketAddress remoteAddress,
				PublicKey serverKey) throws IOException {
			try {
				return super.prepareKnownHostEntry(clientSession, remoteAddress,
						serverKey);
			} catch (Exception e) {
				throw new IOException(e.getMessage(), e);
