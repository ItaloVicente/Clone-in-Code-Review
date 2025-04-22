		return cloneArray(getCachedBytes());
	}

	public final byte[] getBytes(int sizeLimit) throws LargeObjectException
			MissingObjectException
		byte[] cached = getCachedBytes(sizeLimit);
		try {
			return cloneArray(cached);
		} catch (OutOfMemoryError tooBig) {
			throw new LargeObjectException();
		}
