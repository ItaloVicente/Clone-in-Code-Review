	private abstract static class Out extends DfsOutputStream {
		private final ByteArrayOutputStream dst = new ByteArrayOutputStream();

		private byte[] data;

		@Override
		public void write(byte[] buf
			data = null;
			dst.write(buf
		}

		@Override
		public int read(long position
			byte[] d = getData();
			int n = Math.min(buf.remaining()
			if (n == 0)
				return -1;
			buf.put(d
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

