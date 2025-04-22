	 * @throws IOException if inserting the content fails
	 */
	public DirCacheEntry insertToIndex(
			InputStream inputStream,
			byte[] path,
			FileMode fileMode,
			int entryStage,
			Instant lastModified,
			int len,
