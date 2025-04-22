	public void processBitmapForWrite(BitmapCommit c
			int flags) {
		EWAHCompressedBitmap compressed = bitmap.retrieveCompressed();
		compressed.trim();
		StoredBitmap newest = new StoredBitmap(c

		bitmapsToWriteXorBuffer.add(newest);
		if (bitmapsToWriteXorBuffer.size() > MAX_XOR_OFFSET_SEARCH) {
			bitmapsToWrite.add(
					generateStoredEntry(bitmapsToWriteXorBuffer.pollFirst()));
		}
