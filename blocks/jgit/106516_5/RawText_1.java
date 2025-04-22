

	private static final BinaryBlobException binaryBlobException = new BinaryBlobException();
	@Nullable
	public static RawText load(ObjectLoader ldr
		long sz = ldr.getSize();

		if (threshold < FIRST_FEW_BYTES) {
			threshold = FIRST_FEW_BYTES;
		}
		if (sz > threshold) {
			throw binaryBlobException;
		}

		if (sz <= FIRST_FEW_BYTES) {
			byte []data = ldr.getCachedBytes(FIRST_FEW_BYTES);
			if (isBinary(data)) {
				throw binaryBlobException;
			}
			return new RawText(data);
		}

		byte head[] = new byte[FIRST_FEW_BYTES];
		try (InputStream stream = ldr.openStream()) {
			int off = 0;
			int left = head.length;
			while (left > 0) {
				int n = stream.read(head
				if (n < 0) {
					throw new IllegalStateException("negative read");
				}

				left -= n;
				off += n;
			}

			if (isBinary(head)) {
				throw binaryBlobException;
			}

			byte data[];
			try {
				data = new byte[(int)sz];
			} catch (OutOfMemoryError e) {
				throw new LargeObjectException.OutOfMemory(e);
			}

			System.arraycopy(head
			IO.readFully(stream
			return new RawText(data);
		}
	}
