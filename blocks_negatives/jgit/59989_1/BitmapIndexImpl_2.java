			if (isSameCompressedBitmap(other)) {
				bitset.xor(((CompressedBitmap) other).bitmap);
			} else if (isSameCompressedBitmapBuilder(other)) {
				CompressedBitmapBuilder b = (CompressedBitmapBuilder) other;
				bitset.xor(b.bitset.combine());
			} else {
				throw new IllegalArgumentException();
			}
