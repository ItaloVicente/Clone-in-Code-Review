				StagingViewContentProvider contentProvider = getContentProvider((TreeViewer) viewer);
				if (element instanceof StagingEntry)
					return contentProvider.isInFilter((StagingEntry) element);
				else if (element instanceof StagingFolderEntry)
					return contentProvider
							.hasVisibleChildren((StagingFolderEntry) element);
