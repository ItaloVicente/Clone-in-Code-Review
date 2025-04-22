	private EWAHCompressedBitmap retrieveCompressed(Bitmap bitmap) {
		if (bitmap instanceof BitmapBuilder) {
			bitmap = ((BitmapBuilder) bitmap).build();
		}

		if (!(bitmap instanceof CompressedBitmap)) {
			throw new IllegalArgumentException(bitmap.getClass().toString());
		}

		return ((CompressedBitmap) bitmap).getEwahCompressedBitmap();
	}

	public void processBitmapForWrite(BitmapCommit c
			int flags) {
		EWAHCompressedBitmap compressed = retrieveCompressed(bitmap);
		compressed.trim();
		StoredBitmap newest = new StoredBitmap(c

		searchOffset.add(newest);
		if (searchOffset.size() > MAX_XOR_OFFSET_SEARCH) {
			StoredBitmap oldest = searchOffset.pollFirst();
			StoredEntry writeEntry = generateStoredEntry(oldest);
			bitmapsToWrite.add(writeEntry);
		}

		if (c.isAddToIndex()) {
			addBitmap(c
		}
	}

	private StoredEntry generateStoredEntry(StoredBitmap bitmapToWrite) {
		int bestXorOffset = 0;
		EWAHCompressedBitmap bestBitmap = bitmapToWrite.getBitmap();

		int offset = 1;
		for (StoredBitmap curr : searchOffset) {
			EWAHCompressedBitmap bitmap = curr.getBitmap()
					.xor(bitmapToWrite.getBitmap());
			if (bitmap.sizeInBytes() < bestBitmap.sizeInBytes()) {
				bestBitmap = bitmap;
				bestXorOffset = offset;
			}
			offset++;
		}

		PositionEntry entry = positionEntries.get(bitmapToWrite);
		if (entry == null) {
			throw new IllegalStateException();
		}
		bestBitmap.trim();
		StoredEntry result = new StoredEntry(entry.namePosition
				bestXorOffset

		return result;
	}

