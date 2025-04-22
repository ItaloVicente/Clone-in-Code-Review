
	static final class ArrayStream extends OutputStream {
		final byte[] buf;
		int cnt;

		ArrayStream(int max) {
			buf = new byte[max];
		}

		@Override
		public void write(int b) throws IOException {
			if (cnt == buf.length)
				throw new IOException();
			buf[cnt++] = (byte) b;
		}

		@Override
		public void write(byte[] b
			if (len > buf.length - cnt)
				throw new IOException();
			System.arraycopy(b
			cnt += len;
		}
	}
