		private boolean alphabeticSort;

		private Comparator<String> comparator;

		private boolean fileNamesFirst;

		private StagingEntryComparator(boolean alphabeticSort,
				boolean fileNamesFirst) {
			this.alphabeticSort = alphabeticSort;
			this.setFileNamesFirst(fileNamesFirst);
			comparator = CommonUtils.STRING_ASCENDING_COMPARATOR;
		}

		public boolean isFileNamesFirst() {
			return fileNamesFirst;
		}

		public void setFileNamesFirst(boolean fileNamesFirst) {
			this.fileNamesFirst = fileNamesFirst;
		}

		private void setAlphabeticSort(boolean sort) {
			this.alphabeticSort = sort;
		}

		private boolean isAlphabeticSort() {
			return alphabeticSort;
		}
