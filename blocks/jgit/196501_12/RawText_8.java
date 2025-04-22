
	public static RawText loadBinary(ObjectLoader ldr)
			throws IOException {
		long sz = ldr.getSize();

		int bufferSize = getBufferSize();
		if (sz <= bufferSize) {
			byte[] data = ldr.getCachedBytes(bufferSize);
			return new RawText(data);
		}

		byte[] head = new byte[bufferSize];
		try (InputStream stream = ldr.openStream()) {
			int off = 0;
			int left = head.length;
			while (left > 0) {
				int n = stream.read(head
				if (n < 0) {
					throw new EOFException();
				}
				left -= n;

				while (n > 0) {
					off++;
					n--;
				}
			}

			byte[] data;
			try {
				data = new byte[(int)sz];
			} catch (OutOfMemoryError e) {
				throw new LargeObjectException.OutOfMemory(e);
			}

			System.arraycopy(head
			IO.readFully(stream
			return new RawText(data
		}
	}
