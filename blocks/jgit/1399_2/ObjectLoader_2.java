	public byte[] getCachedBytes(int sizeLimit) throws LargeObjectException
			MissingObjectException
		if (!isLarge())
			return getCachedBytes();

		ObjectStream in = openStream();
		try {
			long sz = in.getSize();
			if (sizeLimit < sz || Integer.MAX_VALUE < sz)
				throw new LargeObjectException();

			byte[] buf;
			try {
				buf = new byte[(int) sz];
			} catch (OutOfMemoryError notEnoughHeap) {
				throw new LargeObjectException();
			}

			IO.readFully(in
			return buf;
		} finally {
			in.close();
		}
	}

