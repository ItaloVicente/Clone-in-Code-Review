			return bitmapIndex;
		}

		private EWAHCompressedBitmap ewahBitmap(Bitmap other) {
			if (other instanceof CompressedBitmap) {
				CompressedBitmap b = (CompressedBitmap) other;
				if (b.bitmapIndex != bitmapIndex) {
					throw new IllegalArgumentException();
				}
				return b.bitmap;
			}
			if (other instanceof CompressedBitmapBuilder) {
				CompressedBitmapBuilder b = (CompressedBitmapBuilder) other;
				if (b.bitmapIndex != bitmapIndex) {
					throw new IllegalArgumentException();
				}
				return b.bitset.combine();
			}
			throw new IllegalArgumentException();
