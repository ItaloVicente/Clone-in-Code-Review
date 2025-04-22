				} else if (element instanceof StagingFolderEntry) {
					if (filterText != null) {
						filterString = filterText.getText().trim();
						return ((StagingViewContentProvider) ((TreeViewer) viewer)
								.getContentProvider()).hasVisibleChildren(
								(StagingFolderEntry) element, filterString);
					}
