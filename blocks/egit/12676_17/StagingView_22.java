				Object[] unstagedExpanded = unstagedViewer
						.getExpandedElements();
				Object[] stagedExpanded = stagedViewer
						.getExpandedElements();
				unstagedViewer.setInput(update);
				stagedViewer.setInput(update);
				expandPreviousExpandedAndPaths(unstagedExpanded, unstagedViewer,
						pathsToExpandInUnstaged);
				expandPreviousExpandedAndPaths(stagedExpanded, stagedViewer,
						pathsToExpandInStaged);
