	 * Suggested block size to read from pack files in.
	 * <p>
	 * If a pack file does not have a native block size, this size will be used.
	 * <p>
	 * If a pack file has a native size, a whole multiple of the native size
	 * will be used until it matches this size.
	 * <p>
	 * The value for blockSize must be a power of 2.
