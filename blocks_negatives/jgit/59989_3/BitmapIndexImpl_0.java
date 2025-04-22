			if (isSameCompressedBitmap(other)) {
				bitset.or(((CompressedBitmap) other).bitmap);
			} else if (isSameCompressedBitmapBuilder(other)) {
				CompressedBitmapBuilder b = (CompressedBitmapBuilder) other;
				bitset.or(b.bitset.combine());
			} else {
				throw new IllegalArgumentException();
			}
