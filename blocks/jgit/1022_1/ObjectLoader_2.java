	public abstract byte[] getCachedBytes() throws LargeObjectException;

	public abstract ObjectStream openStream() throws MissingObjectException
			IOException;

	public void copyTo(OutputStream out) throws MissingObjectException
			IOException {
		if (isLarge()) {
			ObjectStream in = openStream();
			try {
				byte[] tmp = new byte[1024];
				long copied = 0;
				for (;;) {
					int n = in.read(tmp);
					if (n < 0)
						break;
					out.write(tmp
					copied += n;
				}
				if (copied != getSize())
					throw new EOFException();
			} finally {
				in.close();
			}
		} else {
			out.write(getCachedBytes());
		}
	}
