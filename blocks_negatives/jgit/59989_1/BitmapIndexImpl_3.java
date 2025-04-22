			return new CompressedBitmap(bitmap.xor(bitmapOf(other)));
		}

		private EWAHCompressedBitmap bitmapOf(Bitmap other) {
			if (isSameCompressedBitmap(other))
				return ((CompressedBitmap) other).bitmap;
			if (isSameCompressedBitmapBuilder(other))
				return ((CompressedBitmapBuilder) other).build().bitmap;
			CompressedBitmapBuilder builder = newBitmapBuilder();
			builder.or(other);
			return builder.build().bitmap;
