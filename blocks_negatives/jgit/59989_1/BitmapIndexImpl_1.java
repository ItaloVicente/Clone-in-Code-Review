			if (isSameCompressedBitmap(other)) {
				bitset.andNot(((CompressedBitmap) other).bitmap);
			} else if (isSameCompressedBitmapBuilder(other)) {
				CompressedBitmapBuilder b = (CompressedBitmapBuilder) other;
				bitset.andNot(b.bitset.combine());
			} else {
				throw new IllegalArgumentException();
			}
