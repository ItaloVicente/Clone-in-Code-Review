		private Duration fsTimestampResolution;

		Duration getFsTimestampResolution() {
			return fsTimestampResolution;
		}

		private FileStoreAttributeCache(FileStore s, Path dir)
				throws IOException, InterruptedException {
