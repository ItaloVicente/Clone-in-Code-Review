
		ViewerFilter filter = new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (element instanceof StagingEntry) {
					if (filterText != null && filterText.getText() != null
							&& filterText.getText().trim().length() > 0) {
						return ((StagingEntry) element)
								.getPath()
								.toUpperCase()
								.contains(
										filterText.getText().trim()
												.toUpperCase());
					}
				}
				return true;
			}
		};
		unstagedTableViewer.addFilter(filter);
		stagedTableViewer.addFilter(filter);
