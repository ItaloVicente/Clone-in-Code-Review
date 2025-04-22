	public List<StoredEntry> getCompressedBitmaps() {
		while (!searchOffset.isEmpty()) {
			StoredEntry writeEntry = generateStoredEntry(
					searchOffset.pollFirst());
			bitmapsToWrite.add(writeEntry);
		}
		Collections.reverse(bitmapsToWrite);
		return bitmapsToWrite;
