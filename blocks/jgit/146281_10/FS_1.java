
		private static class CacheEntry {
			private final Path path;

			private final FileStoreAttributeCache attributeCache;

			private volatile long lastAccessedTimestamp;

			public CacheEntry(Path path
					long lastAccessedTimestamp) {
				this.path = path;
				this.attributeCache = attributeCache;
				this.lastAccessedTimestamp = lastAccessedTimestamp;
			}

			@Override
			public String toString() {
				return lastAccessedTimestamp + " " + path;
			}
		}
