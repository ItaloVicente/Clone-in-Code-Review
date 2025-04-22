	public CompressedBitmap toBitmap(PackBitmapIndex i,
			EWAHCompressedBitmap b) {
		if (i != packIndex) {
			throw new IllegalArgumentException();
		}
		if (b == null) {
