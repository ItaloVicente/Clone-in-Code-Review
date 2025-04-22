		private Duration fsTimestampResolution;

		Duration getFsTimestampResolution() {
			return fsTimestampResolution;
		}

		private FileStoreAttributeCache(Duration fsTimestampResolution) {
			this.fsTimestampResolution = fsTimestampResolution;
