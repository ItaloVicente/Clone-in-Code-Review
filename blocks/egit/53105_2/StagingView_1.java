
				int elementsCount = updateAutoExpand(unstagedViewer,
						getUnstaged(indexDiff));
				elementsCount += updateAutoExpand(stagedViewer,
						getStaged(indexDiff));
				if (elementsCount > 10000) {
					compactTreePresentationAction.setChecked(true);
					switchToCompactModeInternal(true);
				}

