	private static class UnstagedComparator extends ViewerComparator {

		private boolean alphabeticSort;

		private Comparator<String> comparator;

		private UnstagedComparator(boolean alphabeticSort) {
			this.alphabeticSort = alphabeticSort;
			comparator = CommonUtils.STRING_ASCENDING_COMPARATOR;
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
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			int cat1 = category(e1);
			int cat2 = category(e2);

			if (cat1 != cat2) {
				return cat1 - cat2;
			}

			String name1 = getStagingEntryName(e1);
			String name2 = getStagingEntryName(e2);

			return comparator.compare(name1, name2);
		}

		private String getStagingEntryName(Object element) {
			String name = ""; //$NON-NLS-1$
			StagingEntry stagingEntry = getStagingEntry(element);
			if (stagingEntry != null) {
				name = stagingEntry.getName();
			}
			return name;
		}

		@Nullable
		private StagingEntry getStagingEntry(Object element) {
			StagingEntry entry = null;
			if (element instanceof StagingEntry) {
				entry = (StagingEntry) element;
			}
			if (element instanceof TreeItem) {
				TreeItem item = (TreeItem) element;
				if (item.getData() instanceof StagingEntry) {
					entry = (StagingEntry) item.getData();
				}
			}
			return entry;
		}

		private int getState(StagingEntry entry) {
			switch (entry.getState()) {
			case UNTRACKED:
				return 1;
			case MISSING:
				return 2;
			case MODIFIED:
				return 3;
			default:
				return super.category(entry);
			}
		}
	}
