
	/**
	 * Temporary holder of object position in pack index and other metadata for
	 * {@code StoredBitmap}.
	 */
	private static final class IdxPositionBitmap {
		int nthObjectId;
		IdxPositionBitmap xorIdxPositionBitmap;
		EWAHCompressedBitmap bitmap;
		int flags;
		StoredBitmap sb;

		IdxPositionBitmap(int nthObjectId, @Nullable
		IdxPositionBitmap xorIdxPositionBitmap, EWAHCompressedBitmap bitmap,
				int flags) {
			this.nthObjectId = nthObjectId;
			this.xorIdxPositionBitmap = xorIdxPositionBitmap;
			this.bitmap = bitmap;
			this.flags = flags;
		}

		StoredBitmap getXorStoredBitmap() {
			return xorIdxPositionBitmap == null ? null
					: xorIdxPositionBitmap.sb;
		}
	}
