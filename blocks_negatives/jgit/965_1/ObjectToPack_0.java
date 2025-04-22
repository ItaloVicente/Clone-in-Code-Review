	/** Pack to reuse compressed data from, otherwise null. */
	private PackFile copyFromPack;

	/** Offset of the object's header in {@link #copyFromPack}. */
	private long copyOffset;

