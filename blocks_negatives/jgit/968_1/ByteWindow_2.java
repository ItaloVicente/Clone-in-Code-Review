
	protected static final byte[] verifyGarbageBuffer = new byte[2048];

	final void inflateVerify(final long pos, final Inflater inf)
			throws DataFormatException {
		inflateVerify((int) (pos - start), inf);
	}

	protected abstract void inflateVerify(int pos, Inflater inf)
			throws DataFormatException;
