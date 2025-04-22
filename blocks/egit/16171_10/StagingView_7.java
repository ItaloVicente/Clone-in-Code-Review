
				boolean commitAndPushEnabled = commitEnabled
						&& !repository.getRepositoryState().isRebasing();
				commitAndPushButton.setEnabled(commitAndPushEnabled);

				boolean rebaseContinueEnabled = indexDiffAvailable
						&& repository.getRepositoryState().isRebasing()
						&& indexDiff.getConflicting().isEmpty();
				rebaseContinueButton.setEnabled(rebaseContinueEnabled);

