public final class DfsPackFile {
	/** Cache that owns this pack file and its data. */
	private final DfsBlockCache cache;

	/** Description of the pack file's storage. */
	private final DfsPackDescription packDesc;

	/** Unique identity of this pack while in-memory. */
	final DfsStreamKey key;
