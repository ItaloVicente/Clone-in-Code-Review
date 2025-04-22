		public FileStoreAttributes(
				@NonNull Duration fsTimestampResolution) {
			this.fsTimestampResolution = fsTimestampResolution;
			this.minimalRacyInterval = Duration.ZERO;
		}

		@SuppressWarnings({ "nls"
