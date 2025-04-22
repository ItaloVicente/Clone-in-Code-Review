		return addExistingToIndex(
				insertResult(resultStreamLoader, lfsAttribute), path, fileMode,
				entryStage, lastModified, len);
	}

	/**
	 * Adds a path with the specified stage to the index builder.
	 *
	 * @param objectId
	 *            of the existing object to add
	 * @param path
	 *            of the modified file
	 * @param fileMode
	 *            of the modified file
	 * @param entryStage
	 *            of the new entry
	 * @param lastModified
	 *            instant of the modified file
	 * @param len
	 *            of the modified file content
	 * @return the entry which was added to the index
	 */
	public DirCacheEntry addExistingToIndex(ObjectId objectId, byte[] path,
			FileMode fileMode, int entryStage, Instant lastModified, int len) {
