	boolean isSameCompressedBitmap(Bitmap other) {
		if (other instanceof CompressedBitmap) {
			CompressedBitmap b = (CompressedBitmap) other;
			return this == b.getPackBitmapIndex();
		}
		return false;
	}

	boolean isSameCompressedBitmapBuilder(Bitmap other) {
		if (other instanceof CompressedBitmapBuilder) {
			CompressedBitmapBuilder b = (CompressedBitmapBuilder) other;
			return this == b.getBitmapIndex();
		}
		return false;
	}

