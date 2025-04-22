		this(in, detectBinary, false);
	}

	/**
	 * Creates a new InputStream, wrapping the specified stream
	 *
	 * @param in
	 *            raw input stream
	 * @param detectBinary
	 *            whether binaries should be detected
	 * @param abortIfBinary
	 *            throw an IOException if the file is binary
	 * @since 3.2
	 */
	public EolCanonicalizingInputStream(InputStream in, boolean detectBinary,
			boolean abortIfBinary) {
