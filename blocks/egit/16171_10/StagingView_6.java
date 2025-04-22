				enableCommitWidgets(indexDiffAvailable
						&& indexDiff.getConflicting().isEmpty());

				updateRebaseButtonVisibility(repository.getRepositoryState()
						.isRebasing());

				boolean commitEnabled = indexDiffAvailable
						&& repository.getRepositoryState().canCommit()
						&& indexDiff.getConflicting().isEmpty();
