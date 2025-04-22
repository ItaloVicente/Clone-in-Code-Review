		private static final Map<FileStore, FileStoreAttributes> attributeCache = new ConcurrentHashMap<>();

		private static final SimpleLruCache<Path, FileStoreAttributes> attrCacheByPath = new SimpleLruCache<>(
				100, 0.2f);

		private static AtomicBoolean background = new AtomicBoolean();

		private static Map<FileStore, Lock> locks = new ConcurrentHashMap<>();

		private static void setBackground(boolean async) {
			background.set(async);
		}

		private static final String javaVersionPrefix = SystemReader
