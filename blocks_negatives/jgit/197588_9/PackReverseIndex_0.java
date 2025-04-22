public class PackReverseIndex {
	/** Index we were created from, and that has our ObjectId data. */
	private final PackIndex index;

	/** The number of bytes per entry in the offsetIndex. */
	private final long bucketSize;

	/**
	 * An index into the nth mapping, where the value is the position after the
	 * the last index that contains the values of the bucket. For example given
	 * offset o (and bucket = o / bucketSize), the offset will be contained in
	 * the range nth[offsetIndex[bucket - 1]] inclusive to
	 * nth[offsetIndex[bucket]] exclusive.
	 *
	 * See {@link #binarySearch}
	 */
	private final int[] offsetIndex;

	/** Mapping from indices in offset order to indices in SHA-1 order. */
	private final int[] nth;

