	private byte[] getBytesAndCloseStream(InputStream stream) throws Exception {
		try {
			byte[] actualByte = new byte[stream.available()];
			stream.read(actualByte);
			return actualByte;
		} finally {
			stream.close();
		}
