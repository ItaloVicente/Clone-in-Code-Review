		public void setFileNamesFirst(boolean fileNamesFirst) {
			this.fileNamesFirst = fileNamesFirst;
		}

		private void setAlphabeticSort(boolean sort) {
			this.alphabeticSort = sort;
		}

		private boolean isAlphabeticSort() {
			return alphabeticSort;
		}

		@Override
		public int category(Object element) {
			if (!isAlphabeticSort()) {
				StagingEntry stagingEntry = getStagingEntry(element);
				if (stagingEntry != null) {
					return getState(stagingEntry);
				}
			}
			return super.category(element);
