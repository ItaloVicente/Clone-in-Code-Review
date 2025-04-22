	private static class CharDataInputStream extends FilterInputStream {

		private ByteArrayOutputStream data;

		protected CharDataInputStream(InputStream in) {
			super(in);
			data = new ByteArrayOutputStream();
		}

		@Override
		public int read() throws IOException {
			int result = super.read();
			if (result != -1) {
				data.write(result);
			}
			return result;
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			len = super.read(b, off, len);
			if (len != -1) {
				data.write(b, off, len);
			}
			return len;
		}

		@Override
		public long skip(long n) throws IOException {
			byte[] buf = new byte[512];
			long total = 0;
			while (total < n) {
				long len = n - total;
				len = read(buf, 0, len < buf.length ? (int) len : buf.length);
				if (len == -1) {
					return total;
				}
				total += len;
			}
			return total;
		}

		public String toString(String charsetName) throws UnsupportedEncodingException {
			return data.toString(charsetName);
		}

	}

