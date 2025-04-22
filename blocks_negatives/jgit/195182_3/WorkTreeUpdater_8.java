	 * @throws IOException if inserting the content fails
	 */
	public DirCacheEntry insertToIndex(
			StreamLoader resultStreamLoader,
			byte[] path,
			FileMode fileMode,
			int entryStage,
			Instant lastModified,
			int len,
			Attribute lfsAttribute) throws IOException {
		return addExistingToIndex(insertResult(resultStreamLoader, lfsAttribute),
				path, fileMode, entryStage, lastModified, len);
