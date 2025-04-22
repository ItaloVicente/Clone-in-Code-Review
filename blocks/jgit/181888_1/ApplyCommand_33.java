
	private static class SHA1InputStream extends InputStream {

		private final SHA1 hash;

		private final InputStream in;

		SHA1InputStream(SHA1 hash
			this.hash = hash;
			this.in = in;
		}

		@Override
		public int read() throws IOException {
			int b = in.read();
			if (b >= 0) {
				hash.update((byte) b);
			}
			return b;
		}

		@Override
		public int read(byte[] b
			int n = in.read(b
			if (n > 0) {
				hash.update(b
			}
			return n;
		}

		@Override
		public void close() throws IOException {
			in.close();
		}
	}
