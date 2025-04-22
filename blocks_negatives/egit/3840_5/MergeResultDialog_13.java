		viewer.setLabelProvider(new ITableLabelProvider() {

			public void removeListener(ILabelProviderListener listener) {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void dispose() {
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public String getColumnText(Object element, int columnIndex) {
				ObjectId commitId = (ObjectId) element;
				if (columnIndex == 0)
					return abbreviate(commitId, false);
				else if (columnIndex == 1)
					return getCommitMessage(commitId);
				return EMPTY;
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}
		});
