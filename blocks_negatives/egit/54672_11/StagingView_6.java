				updateRebaseButtonVisibility(repository.getRepositoryState()
						.isRebasing());


				boolean commitEnabled = indexDiffAvailable
						&& repository.getRepositoryState().canCommit()
						&& noConflicts;
				commitButton.setEnabled(commitEnabled);
