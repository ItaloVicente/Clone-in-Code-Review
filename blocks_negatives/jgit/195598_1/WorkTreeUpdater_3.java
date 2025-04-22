	 * @param inputStream
	 *            with the content to be updated
	 * @param path
	 *            of the file to be updated
	 * @param fileMode
	 *            of the modified file
	 * @param entryStage
	 *            of the new entry
	 * @param lastModified
	 *            instant of the modified file
	 * @param len
	 *            of the content
	 * @param lfsAttribute
	 *            for checking for LFS enablement
	 * @return the entry which was added to the index
	 * @throws IOException
	 *             if inserting the content fails
	 */
	public DirCacheEntry insertToIndex(InputStream inputStream, byte[] path,
			FileMode fileMode, int entryStage, Instant lastModified, int len,
			Attribute lfsAttribute) throws IOException {
		StreamLoader contentLoader = createStreamLoader(() -> inputStream, len);
		return insertToIndex(contentLoader, path, fileMode, entryStage,
				lastModified, len, lfsAttribute);
	}

	/**
	 * Creates a path with the given content, and adds it to the specified stage
	 * to the index builder.
	 *
	 * @param resultStreamLoader
	 *            with the content to be updated
