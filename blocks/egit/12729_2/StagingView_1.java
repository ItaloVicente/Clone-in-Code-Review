
		ViewerFilter filter = new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (element instanceof StagingEntry) {
					if (searchText != null && searchText.getText() != null
							&& searchText.getText().trim().length() > 0) {
						return ((StagingEntry) element)
								.getPath()
								.toUpperCase()
								.contains(
										searchText.getText().trim()
												.toUpperCase());
					}
				}
				return true;
			}
		};
		unstagedTableViewer.addFilter(filter);
		stagedTableViewer.addFilter(filter);
