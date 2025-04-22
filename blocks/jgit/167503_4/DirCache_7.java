		if (version == null && this.repository != null) {
			DirCacheConfig config = repository.getConfig()
					.get(DirCacheConfig::new);
			version = config.getIndexVersion();
		}
		if (version == null
				|| version == DirCacheVersion.DIRC_VERSION_MINIMUM) {
			version = DirCacheVersion.DIRC_VERSION_MINIMUM;
			for (int i = 0; i < entryCnt; i++) {
				if (sortedEntries[i].isExtended()) {
					version = DirCacheVersion.DIRC_VERSION_EXTENDED;
					break;
				}
