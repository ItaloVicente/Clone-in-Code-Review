			if (repositoryChanged) {
				resetPathsToExpand();
				if (refsChangedListener != null)
					refsChangedListener.remove();
				refsChangedListener = repository.getListenerList()
						.addRefsChangedListener(
								event -> updateRebaseButtonVisibility(repository
										.getRepositoryState().isRebasing()));
			}
			final StagingViewUpdate update = new StagingViewUpdate(repository,
					indexDiff, null);
			Object[] unstagedExpanded = unstagedViewer
					.getVisibleExpandedElements();
			Object[] stagedExpanded = stagedViewer.getVisibleExpandedElements();

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
