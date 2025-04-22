	/**
	 * Determines whether the file was committed un-normalized. If the iterator
	 * points to a conflict entry, checks the "ours" version.
	 *
	 * @param dirCache
	 *            iterator pointing to the current entry for the file in the
	 *            index
	 * @return {@code true} if the file in the index is not binary and has CR/LF
	 *         line endings, {@code false} otherwise
	 */
	private boolean hasCrLfInIndex(DirCacheIterator dirCache) {
		if (dirCache == null) {
			return false;
		}
		DirCacheEntry entry = dirCache.getDirCacheEntry();
		if ((entry.getRawMode() & FileMode.TYPE_MASK) == FileMode.TYPE_FILE) {
			ObjectId blobId = entry.getObjectId();
			if (entry.getStage() > 0
					&& entry.getStage() != DirCacheEntry.STAGE_2) {
				blobId = null;
				byte[] name = entry.getRawPath();
				int i = 0;
				while (!dirCache.eof()) {
					dirCache.next(1);
					i++;
					entry = dirCache.getDirCacheEntry();
					if (entry == null
							|| !Arrays.equals(name, entry.getRawPath())) {
						break;
					}
					if (entry.getStage() == DirCacheEntry.STAGE_2) {
						if ((entry.getRawMode()
								& FileMode.TYPE_MASK) == FileMode.TYPE_FILE) {
							blobId = entry.getObjectId();
						}
						break;
					}
				}
				dirCache.back(i);
			}
			if (blobId != null) {
				try (ObjectReader reader = repository.newObjectReader()) {
					ObjectLoader loader = reader.open(blobId,
							Constants.OBJ_BLOB);
					try {
						return RawText.isCrLfText(loader.getCachedBytes());
					} catch (LargeObjectException e) {
						try (InputStream in = loader.openStream()) {
							return RawText.isCrLfText(in);
						}
					}
				} catch (IOException e) {
				}
			}
		}
		return false;
	}

