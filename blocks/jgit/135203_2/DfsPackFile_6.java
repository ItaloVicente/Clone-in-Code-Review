
	private static class CachedIndices {
		final PackIndex index;

		@Nullable
		final PackReverseIndex reverseIdx;

		@Nullable
		final PackBitmapIndex bitmapIdx;

		CachedIndices(PackIndex index
				@Nullable PackBitmapIndex bitmapIdx) {
			this.index = index;
			this.reverseIdx = reverseIdx;
			this.bitmapIdx = bitmapIdx;
		}
	}
