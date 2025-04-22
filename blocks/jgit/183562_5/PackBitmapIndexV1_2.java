
	private static final class IdxPositionBitmap {
		int nthObjectId;

		IdxPositionBitmap xorIdxPositionBitmap;

		EWAHCompressedBitmap bitmap;

		int flags;

		StoredBitmap sb;

		IdxPositionBitmap(int nthObjectId
		IdxPositionBitmap xorIdxPositionBitmap
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
