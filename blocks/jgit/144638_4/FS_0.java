		private static AtomicBoolean background = new AtomicBoolean();

		private static Map<FileStore

		private static void setBackground(boolean async) {
			background.set(async);
		}

		private static Duration getFsTimestampResolution(Path file) {
			Path dir = Files.isDirectory(file) ? file : file.getParent();
			if (!dir.toFile().canWrite()) {
				return FALLBACK_TIMESTAMP_RESOLUTION;
			}

