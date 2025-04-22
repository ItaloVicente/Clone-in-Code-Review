
	private static class ResponseBufferedOutputStream extends OutputStream {
		private final OutputStream rawOut;

		private OutputStream out;
		@Nullable
		private ByteArrayOutputStream buffer;

		ResponseBufferedOutputStream(OutputStream rawOut) {
			this.rawOut = rawOut;
			this.out = this.buffer = new ByteArrayOutputStream();
		}

		@Override
		public void write(int b) throws IOException {
			out.write(b);
		}

		@Override
		public void write(byte b[]) throws IOException {
			out.write(b);
		}

		@Override
		public void write(byte b[]
			out.write(b
		}

		@Override
		public void flush() throws IOException {
			out.flush();
		}

		@Override
		public void close() throws IOException {
			out.close();
		}

		void stopBuffering() throws IOException {
			if (buffer != null) {
				buffer.writeTo(rawOut);
				buffer = null;
				out = rawOut;
			}
		}
	}
