				setStagingViewerInput(unstagedViewer, update, unstagedExpanded,
						pathsToExpandInUnstaged);
				setStagingViewerInput(stagedViewer, update, stagedExpanded,
						pathsToExpandInStaged);
				resetPathsToExpand();
				refreshAction.setEnabled(true);

				updateRebaseButtonVisibility(repository.getRepositoryState()
						.isRebasing());
