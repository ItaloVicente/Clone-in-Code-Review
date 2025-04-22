
	private static class MessageWriter extends Writer {
		private final ByteArrayOutputStream buf;

		private final OutputStreamWriter enc;

		MessageWriter() {
			buf = new ByteArrayOutputStream();
			enc = new OutputStreamWriter(buf
		}

		@Override
		public void write(char[] cbuf
			synchronized (buf) {
				enc.write(cbuf
				enc.flush();
			}
		}

		OutputStream getRawStream() {
			return buf;
		}

		@Override
		public void close() throws IOException {
		}

		@Override
		public void flush() throws IOException {
		}

		@Override
		public String toString() {
			return RawParseUtils.decode(buf.toByteArray());
		}
	}
