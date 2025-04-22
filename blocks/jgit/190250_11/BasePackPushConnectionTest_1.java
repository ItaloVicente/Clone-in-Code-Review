
	private static class FailingBasePackConnection extends BasePackConnection {
		@SuppressWarnings("InputStreamSlowMultibyteRead")
		FailingBasePackConnection(IOException ioException) {
			super(new TransportLocal(new URIish()
			pckIn = new PacketLineIn(new InputStream() {
				@Override
				public int read() throws IOException {
					throw ioException;
				}
			});
		}
	}
}
