		if (bitmap instanceof BitmapBuilder)
			bitmap = ((BitmapBuilder) bitmap).build();

		EWAHCompressedBitmap compressed;
		if (bitmap instanceof CompressedBitmap)
			compressed = ((CompressedBitmap) bitmap).getEwahCompressedBitmap();
		else
			throw new IllegalArgumentException(bitmap.getClass().toString());

