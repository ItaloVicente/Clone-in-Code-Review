	public List<StoredEntry> getCompressedBitmaps() {
		int index = bitmapsToWrite.size() - 1 - numBitmapsAdded;
		while (!bitmapsToWriteXorBuffer.isEmpty()) {
			bitmapsToWrite.set(index
					generateStoredEntry(bitmapsToWriteXorBuffer.pollFirst()));
			index--;
			numBitmapsAdded++;
		}
		return bitmapsToWrite;
