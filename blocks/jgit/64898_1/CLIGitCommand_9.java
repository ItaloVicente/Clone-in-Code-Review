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
		}

		public List<String> outLines() {
			return IO.readLines(out.toString());
		}

		public String errString() {
			return err.toString();
		}

		public List<String> errLines() {
			return IO.readLines(err.toString());
		}
	}

