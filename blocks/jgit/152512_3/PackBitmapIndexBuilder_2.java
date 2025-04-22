	public List<StoredEntry> getCompressedBitmaps() {
		while (!searchOffset.isEmpty()) {
			StoredBitmap oldest = searchOffset.pollFirst();
			StoredEntry writeEntry = generateStoredEntry(oldest);
			bitmapsToWrite.add(writeEntry);
		}
		Collections.reverse(bitmapsToWrite);
		return bitmapsToWrite;
