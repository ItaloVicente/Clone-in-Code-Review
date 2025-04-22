	private void copyToStream(long position, final byte[] buf, long cnt,
			final OutputStream out, final WindowCursor curs)
			throws IOException, EOFException {
		while (cnt > 0) {
			final int toRead = (int) Math.min(cnt, buf.length);
			readFully(position, buf, 0, toRead, curs);
			position += toRead;
			cnt -= toRead;
			out.write(buf, 0, toRead);
		}
	}
