	public List<StoredEntry> getCompressedBitmaps() {
		while (!bitmapsToWriteXorBuffer.isEmpty()) {
			bitmapsToWrite.add(
					generateStoredEntry(bitmapsToWriteXorBuffer.pollFirst()));
		}
		Collections.reverse(bitmapsToWrite);
		return bitmapsToWrite;
