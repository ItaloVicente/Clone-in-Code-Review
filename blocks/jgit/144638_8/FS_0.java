		private static AtomicBoolean background = new AtomicBoolean(true);

		private static Map<FileStore

		private static void setBackground(boolean async) {
			background.set(async);
		}

		private static Duration getFsTimestampResolution(Path file) {
			Path dir = Files.isDirectory(file) ? file : file.getParent();
			FileStore s;
