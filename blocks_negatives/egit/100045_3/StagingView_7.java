				final IndexDiffData indexDiff = doReload(repository);
				boolean indexDiffAvailable = indexDiffAvailable(indexDiff);
				boolean noConflicts = noConflicts(indexDiff);

				if (repositoryChanged) {
					resetPathsToExpand();
					if (refsChangedListener != null)
						refsChangedListener.remove();
					refsChangedListener = repository.getListenerList()
							.addRefsChangedListener(new RefsChangedListener() {

								@Override
								public void onRefsChanged(RefsChangedEvent event) {
									updateRebaseButtonVisibility(repository
											.getRepositoryState().isRebasing());
								}

							});
				}
				final StagingViewUpdate update = new StagingViewUpdate(repository, indexDiff, null);
				Object[] unstagedExpanded = unstagedViewer.getVisibleExpandedElements();
				Object[] stagedExpanded = stagedViewer.getVisibleExpandedElements();
