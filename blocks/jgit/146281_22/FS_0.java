		private static class CacheEntry {

			private final Path path;

			private final FileStoreAttributes attributes;

			private volatile long lastAccessed;

			CacheEntry(Path path
					long lastAccessed) {
				this.path = path;
				this.attributes = attributes;
				this.lastAccessed = lastAccessed;
			}

			@SuppressWarnings("nls")
			@Override
			public String toString() {
				return "CacheEntry [lastAccessed="
						+ lastAccessed + "
						+ "
			}
		}

