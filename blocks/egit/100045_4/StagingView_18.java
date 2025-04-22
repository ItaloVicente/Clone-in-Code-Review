			setStagingViewerInput(unstagedViewer, update, unstagedExpanded,
					pathsToExpandInUnstaged);
			setStagingViewerInput(stagedViewer, update, stagedExpanded,
					pathsToExpandInStaged);
			resetPathsToExpand();
			unstagedViewer.setSelection(unstagedViewer.getSelection());
			refreshAction.setEnabled(true);
