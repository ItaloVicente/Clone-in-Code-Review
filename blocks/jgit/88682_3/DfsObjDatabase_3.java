		if (replaces != null && !replaces.isEmpty()) {
			DfsBlockCache cache = DfsBlockCache.getInstance();
			for (DfsPackDescription replace : replaces) {
				DfsPackFile packFile = cache.getOrCreate(replace
				packFile.close();
			}
		}
