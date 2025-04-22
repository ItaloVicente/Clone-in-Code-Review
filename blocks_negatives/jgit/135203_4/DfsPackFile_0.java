	/** Index mapping {@link ObjectId} to position within the pack stream. */
	private volatile DfsBlockCache.Ref<PackIndex> index;

	/** Reverse version of {@link #index} mapping position to {@link ObjectId}. */
	private volatile DfsBlockCache.Ref<PackReverseIndex> reverseIndex;

	/** Index of compressed bitmap mapping entire object graph. */
	private volatile DfsBlockCache.Ref<PackBitmapIndex> bitmapIndex;
