		@Override
		protected String prepareModifiedServerKeyLine(
				ClientSession clientSession, SocketAddress remoteAddress,
				KnownHostEntry entry, String curLine, PublicKey expected,
				PublicKey actual) throws IOException {
			try {
				return super.prepareModifiedServerKeyLine(clientSession,
						remoteAddress, entry, curLine, expected, actual);
			} catch (Exception e) {
				throw new IOException(e.getMessage(), e);
			}
