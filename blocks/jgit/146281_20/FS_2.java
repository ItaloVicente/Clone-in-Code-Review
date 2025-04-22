		private static class CacheEntry {

			private final Path path;

			private final FileStoreAttributes attributes;

			private volatile long lastAccessedTimestamp;

			CacheEntry(Path path
					long lastAccessedTimestamp) {
				this.path = path;
				this.attributes = attributes;
				this.lastAccessedTimestamp = lastAccessedTimestamp;
			}

			@SuppressWarnings("nls")
			@Override
			public String toString() {
				return "CacheEntry [lastAccessedTimestamp="
						+ lastAccessedTimestamp + "
						+ "
						+ "]";
			}
		}
