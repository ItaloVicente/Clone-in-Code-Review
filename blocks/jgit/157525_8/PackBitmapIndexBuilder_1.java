	public void processBitmapForWrite(BitmapCommit c
			int flags) {
		int index = bitmapsToWrite.size() - 1 - numBitmapsAdded;
		EWAHCompressedBitmap compressed = retrieveCompressed(bitmap);
		compressed.trim();
		StoredBitmap newest = new StoredBitmap(c

		bitmapsToWriteXorBuffer.add(newest);
		if (bitmapsToWriteXorBuffer.size() > MAX_XOR_OFFSET_SEARCH) {
			bitmapsToWrite.set(index
					generateStoredEntry(bitmapsToWriteXorBuffer.pollFirst()));
			numBitmapsAdded++;
		}

		if (c.isAddToIndex()) {
			addBitmap(c
		}
	}

	private StoredEntry generateStoredEntry(StoredBitmap bitmapToWrite) {
		int bestXorOffset = 0;
		EWAHCompressedBitmap bestBitmap = bitmapToWrite.getBitmap();

		int offset = 1;
		for (StoredBitmap curr : bitmapsToWriteXorBuffer) {
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

