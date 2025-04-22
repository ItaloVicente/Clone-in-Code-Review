	final DfsStreamKey bitmapKey = new DfsStreamKey();

	/**
	 * Total number of bytes in this pack file.
	 * <p>
	 * This field initializes to -1 and gets populated when a block is loaded.
	 */
	volatile long length;

	/**
	 * Preferred alignment for loading blocks from the backing file.
	 * <p>
	 * It is initialized to 0 and filled in on the first read made from the
	 * file. Block sizes may be odd, e.g. 4091, caused by the underling DFS
	 * storing 4091 user bytes and 5 bytes block metadata into a lower level
	 * 4096 byte block on disk.
	 */
	private volatile int blockSize;

	/** True once corruption has been detected that cannot be worked around. */
	private volatile boolean invalid;
