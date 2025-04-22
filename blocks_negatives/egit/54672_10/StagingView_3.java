
				boolean commitEnabled = indexDiffAvailable
						&& repository.getRepositoryState().canCommit()
						&& noConflicts;
				commitButton.setEnabled(commitEnabled);

				boolean commitAndPushEnabled = commitEnabled
						&& !repository.getRepositoryState().isRebasing();
				commitAndPushButton.setEnabled(commitAndPushEnabled);
