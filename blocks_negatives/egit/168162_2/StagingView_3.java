		private int getState(Object stagingObject, int fallback) {
			if (stagingObject instanceof StagingFolderEntry) {
			}
			if (stagingObject instanceof StagingEntry) {
				if (isAlphabeticSort()) {
					return 0;
				}
				switch (((StagingEntry) stagingObject).getState()) {
				case CONFLICTING:
					return 1;
				case MODIFIED:
					return 2;
				case MODIFIED_AND_ADDED:
					return 3;
				case MODIFIED_AND_CHANGED:
					return 4;
				case ADDED:
					return 5;
				case CHANGED:
					return 6;
				case MISSING:
					return 7;
				case MISSING_AND_CHANGED:
					return 8;
				case REMOVED:
					return 9;
				case UNTRACKED:
					return 10;
				}
			}

			return fallback;
		}

