		if (replaces != null) {
			DfsBlockCache cache = DfsBlockCache.getInstance();
			for (DfsPackDescription replace : replaces) {
				DfsPackFile packFile = cache.getOrCreate(replace
				packFile.close();
			}
		}
