public final class DfsPackFile {
	/**
	 * File offset used to cache {@link #index} in {@link DfsBlockCache}.
	 * <p>
	 * To better manage memory, the forward index is stored as a single block in
	 * the block cache under this file position. A negative value is used
	 * because it cannot occur in a normal pack file, and it is less likely to
	 * collide with a valid data block from the file as the high bits will all
	 * be set when treated as an unsigned long by the cache code.
	 */
	private static final long POS_INDEX = -1;

	/** Offset used to cache {@link #reverseIndex}. See {@link #POS_INDEX}. */
	private static final long POS_REVERSE_INDEX = -2;

	/** Offset used to cache {@link #bitmapIndex}. See {@link #POS_INDEX}. */
	private static final long POS_BITMAP_INDEX = -3;

	/** Cache that owns this pack file and its data. */
	private final DfsBlockCache cache;

	/** Description of the pack file's storage. */
	private final DfsPackDescription packDesc;

	/** Unique identity of this pack while in-memory. */
	final DfsPackKey key;

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
