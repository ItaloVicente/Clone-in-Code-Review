				} else {
					StagingViewerUpdate stagingViewerUpdate = updateSelection(
							stagingViewer, contentProvider, oldPaths, newPaths);

					if (stagingViewerUpdate == StagingViewerUpdate.REMOVED) {
						keepSelectionVisible = true;
					} else if (stagingViewerUpdate == StagingViewerUpdate.ADDED) {
						preserveTop = false;
					}
