		private static final Duration FALLBACK_MIN_RACY_INTERVAL = Duration
				.ofMillis(10);

		public static FileStoreAttributeCache get(Path path) {
			path = path.toAbsolutePath();
			Path dir = Files.isDirectory(path) ? path : path.getParent();
			return getFileAttributeCache(dir);
		}

		private static FileStoreAttributeCache getFileAttributeCache(Path dir) {
