		public static FileStoreAttributeCache get(Path path) {
			path = path.toAbsolutePath();
			Path dir = Files.isDirectory(path) ? path : path.getParent();
			return getFileAttributeCache(dir);
		}

		private static FileStoreAttributeCache getFileAttributeCache(Path dir) {
