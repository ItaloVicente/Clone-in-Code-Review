		private static AtomicBoolean background = new AtomicBoolean();

		private static Map<FileStore

		private static void setBackground(boolean async) {
			background.set(async);
		}

		private static final String javaVersionPrefix = System

		private static final Duration FALLBACK_MIN_RACY_INTERVAL = Duration
				.ofMillis(10);

		public static void configureAttributesPathCache(int maxSize
				float purgeFactor) {
			FileStoreAttributes.attrCacheByPath.configure(maxSize
		}

		public static FileStoreAttributes get(Path path) {
			path = path.toAbsolutePath();
			Path dir = Files.isDirectory(path) ? path : path.getParent();
			FileStoreAttributes cached = attrCacheByPath.get(dir);
			if (cached != null) {
				return cached;
			}
			FileStoreAttributes attrs = getFileStoreAttributes(dir);
			attrCacheByPath.put(dir
			return attrs;
		}

		private static FileStoreAttributes getFileStoreAttributes(Path dir) {
			FileStore s;
