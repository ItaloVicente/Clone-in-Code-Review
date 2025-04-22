
	public static class Filter extends ObjectStream {
		private final int type;

		private final long size;

		private final InputStream in;

		public Filter(int type
			this.type = type;
			this.size = size;
			this.in = in;
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public int available() throws IOException {
			return in.available();
		}

		@Override
		public long skip(long n) throws IOException {
			return in.skip(n);
		}

		@Override
		public int read() throws IOException {
			return in.read();
		}

		@Override
		public int read(byte[] b
			return in.read(b
		}

		@Override
		public boolean markSupported() {
			return in.markSupported();
		}

		@Override
		public void mark(int readlimit) {
			in.mark(readlimit);
		}

		@Override
		public void reset() throws IOException {
			in.reset();
		}

		@Override
		public void close() throws IOException {
			in.close();
		}
	}
