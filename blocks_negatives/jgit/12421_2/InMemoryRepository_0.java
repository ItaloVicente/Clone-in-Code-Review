	private abstract static class Out extends DfsOutputStream {
		private final ByteArrayOutputStream dst = new ByteArrayOutputStream();

		private byte[] data;

		@Override
		public void write(byte[] buf, int off, int len) {
			data = null;
			dst.write(buf, off, len);
		}

		@Override
		public int read(long position, ByteBuffer buf) {
			byte[] d = getData();
			int n = Math.min(buf.remaining(), d.length - (int) position);
			if (n == 0)
				return -1;
			buf.put(d, (int) position, n);
			return n;
		}

		byte[] getData() {
			if (data == null)
				data = dst.toByteArray();
			return data;
		}

		@Override
		public abstract void flush();

		@Override
		public void close() {
			flush();
		}

	}

