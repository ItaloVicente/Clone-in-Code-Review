
	public InputStream openInputStream(final long position) {
		return new ReadBackStream(this
	}

	private static class ReadBackStream extends InputStream {
		private final DfsOutputStream os;
		private ByteBuffer buf;
		private long position;

		private ReadBackStream(DfsOutputStream os
			this.os = os;
			this.position = position;
		}

		private void prepare() throws IOException {
			if (buf == null) {
				int bs = os.blockSize();
				buf = ByteBuffer.allocate(bs > 0 ? bs : 8192);
				os.read(position
				buf.flip();
			}
		}

		@Override
		public int read(byte[] b
			prepare();
			int p = off;
			int n = 0;
			while (true) {
				if (len <= buf.remaining()) {
					buf.get(b
					return n + len;
				}
				int r = buf.remaining();
				buf.get(b
				n += r;
				p += r;
				buf.rewind();
				position += buf.capacity();
				int nr = os.read(position
				if (nr < 0) {
					buf.limit(0);
					return n > 0 ? n : -1;
				}
				buf.flip();
			}
		}

		@Override
		public int read() throws IOException {
			prepare();
			if (buf.remaining() > 0) {
				return buf.get();
			}
			buf.rewind();
			position += buf.capacity();
			int nr = os.read(position
			if (nr < 0) {
				buf.limit(0);
				return -1;
			}
			buf.flip();
			return buf.get();
		}
	}
