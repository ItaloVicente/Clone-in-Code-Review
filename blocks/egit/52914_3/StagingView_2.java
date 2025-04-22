	private class UnstagedComparator extends ViewerComparator {

		private boolean alphabeticallySort;

		public UnstagedComparator(boolean sortAlphabetically) {
			this.alphabeticallySort = sortAlphabetically;
		}

		public void setAlphabeticallySortActive(boolean sort) {
			this.alphabeticallySort = sort;
		}

		public boolean isAlphabeticallySortActive() {
			return alphabeticallySort;
		}

		@Override
		public int category(Object element) {

			if (!isAlphabeticallySortActive()) {
				if (element instanceof TreeItem) {
					TreeItem item = (TreeItem) element;
					if (item.getData() instanceof StagingEntry) {
						return getState(item.getData());
					}
				}
				if (element instanceof StagingEntry) {
					return getState(element);
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

			String name1 = getLabel(viewer, e1);
			String name2 = getLabel(viewer, e2);

			name1 = filterChangeChar(name1);
			name2 = filterChangeChar(name2);

			return getComparator().compare(name1, name2);
		}

		private String filterChangeChar(String name) {
			String test = name.toLowerCase();
			if (test.startsWith("> ") && test.length() > 1) { //$NON-NLS-1$
				return name.substring(2);
			}
			return name;
		}

		private String getLabel(Viewer viewer, Object e1) {
			String name;
			if (viewer == null || !(viewer instanceof ContentViewer)) {
				name = e1.toString();
			} else {
				IBaseLabelProvider prov = ((ContentViewer) viewer)
						.getLabelProvider();
				if (prov instanceof ILabelProvider) {
					ILabelProvider lprov = (ILabelProvider) prov;
					name = lprov.getText(e1);
				} else {
					name = e1.toString();
				}
			}
			if (name == null) {
				name = "";//$NON-NLS-1$
			}
			return name;
		}

		private int getState(Object element) {
			final StagingEntry entry = (StagingEntry) element;
			switch (entry.getState()) {
			case UNTRACKED:
				return 1;
			case MISSING:
				return 2;
			case MODIFIED:
				return 3;
			default:
				return super.category(element);
			}
		}

	}

