	public static class Result {
		public final ByteArrayOutputStream out = new ByteArrayOutputStream();

		public final ByteArrayOutputStream err = new ByteArrayOutputStream();

		public Exception ex;

		public byte[] outBytes() {
			return out.toByteArray();
		}

		public byte[] errBytes() {
			return err.toByteArray();
		}

		public String outString() {
			return out.toString();
