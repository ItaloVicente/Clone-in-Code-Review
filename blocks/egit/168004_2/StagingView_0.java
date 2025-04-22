			if (element instanceof StagingFolderEntry) {
				return element;
			}
			return null;
		}

		private int getState(Object stagingObject, int fallback) {
			if (stagingObject instanceof StagingFolderEntry) {
				return -1; // sort folders before files
