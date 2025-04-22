	private boolean has_crlf_in_index(DirCacheIterator dirCache) {
		if (dirCache == null) {
			return false;
		}
		DirCacheEntry entry = dirCache.getDirCacheEntry();
		if (FileMode.REGULAR_FILE.equals(entry.getFileMode())) {
			try (ObjectReader reader = repository.newObjectReader()) {
				ObjectLoader loader = reader.open(entry.getObjectId()
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
		return false;
	}

