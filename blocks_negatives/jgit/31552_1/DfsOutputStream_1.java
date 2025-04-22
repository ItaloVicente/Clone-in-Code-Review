
	/**
	 * Open a stream to read back a portion of already written data.
	 * <p>
	 * The writing position of the output stream is not affected by a read. The
	 * input stream can be read up to the current writing position. Closing the
	 * returned stream has no effect on the underlying {@link DfsOutputStream}.
	 *
	 * @param position
	 *            offset to read from.
	 * @return new input stream
	 */
	public InputStream openInputStream(final long position) {
		return new ReadBackStream(position);
	}

	private class ReadBackStream extends InputStream {
		private final ByteBuffer buf;
		private long position;

		private ReadBackStream(long position) {
			int bs = blockSize();
			this.position = position;
			buf = ByteBuffer.allocate(bs > 0 ? bs : 8192);
			buf.position(buf.limit());
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			int cnt = 0;
			while (0 < len) {
				if (!buf.hasRemaining()) {
					buf.rewind();
					int nr = DfsOutputStream.this.read(position, buf);
					if (nr < 0) {
						buf.position(buf.limit());
						break;
					}
					position += nr;
					buf.flip();
				}
				int n = Math.min(len, buf.remaining());
				buf.get(b, off, n);
				off += n;
				len -= n;
				cnt += n;
			}
			if (cnt == 0 && len > 0) return -1;
			return cnt;
		}

		@Override
		public int read() throws IOException {
			byte[] b = new byte[1];
			int n = read(b);
			return n == 1 ? b[0] & 0xff : -1;
		}
	}
