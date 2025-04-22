	private boolean hasCrLfInIndex(DirCacheIterator dirCache) {
		if (dirCache == null) {
			return false;
		}
		DirCacheEntry entry = dirCache.getDirCacheEntry();
		if (FileMode.REGULAR_FILE.equals(entry.getFileMode())) {
			ObjectId blobId = entry.getObjectId();
			if (entry.getStage() > 0
					&& entry.getStage() != DirCacheEntry.STAGE_2) {
				byte[] name = entry.getRawPath();
				int i = 0;
				while (!dirCache.eof()) {
					dirCache.next(1);
					i++;
					entry = dirCache.getDirCacheEntry();
					if (!Arrays.equals(name
						break;
					}
					if (entry.getStage() == DirCacheEntry.STAGE_2) {
						blobId = entry.getObjectId();
						break;
					}
				}
				dirCache.back(i);
			}
			try (ObjectReader reader = repository.newObjectReader()) {
				ObjectLoader loader = reader.open(blobId
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

