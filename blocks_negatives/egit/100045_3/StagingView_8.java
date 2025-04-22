				int unstagedElementsCount = updateAutoExpand(unstagedViewer,
						getUnstaged(indexDiff));
				int stagedElementsCount = updateAutoExpand(stagedViewer,
						getStaged(indexDiff));
				int elementsCount = unstagedElementsCount + stagedElementsCount;

				if (elementsCount > getMaxLimitForListMode()) {
					listPresentationAction.setEnabled(false);
					if (presentation == Presentation.LIST) {
						compactTreePresentationAction.setChecked(true);
						switchToCompactModeInternal(true);
					} else {
						setExpandCollapseActionsVisible(false,
								unstagedElementsCount <= getMaxLimitForListMode(),
								true);
						setExpandCollapseActionsVisible(true,
								stagedElementsCount <= getMaxLimitForListMode(),
								true);
					}
