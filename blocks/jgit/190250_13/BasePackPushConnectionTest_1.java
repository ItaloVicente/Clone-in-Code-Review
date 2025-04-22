
	private static class FailingBasePackConnection extends BasePackConnection {
		FailingBasePackConnection() {
			super(new TransportLocal(new URIish()
					new java.io.File("")));
			pckIn = new PacketLineIn(new ByteArrayInputStream(new byte[0]));
		}
	}
}
