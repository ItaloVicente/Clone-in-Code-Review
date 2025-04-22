				StagingViewerUpdate stagingViewerUpdate = updateSelection(
						stagingViewer, contentProvider, oldPaths,
						buildElementMap(stagingViewer, contentProvider,
								comparator));

				if (stagingViewerUpdate == StagingViewerUpdate.REMOVED) {
					keepSelectionVisible = true;
				} else if (stagingViewerUpdate == StagingViewerUpdate.ADDED) {
