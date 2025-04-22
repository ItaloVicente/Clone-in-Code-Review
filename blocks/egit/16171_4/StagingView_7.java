				boolean commitAndPushEnabled = commitEnabled
						&& !repository.getRepositoryState().isRebasing();
				commitAndPushButton.setEnabled(commitAndPushEnabled);
				try {
					boolean rebaseContinueEnabled = indexDiffAvailable
							&& repository.getRepositoryState().isRebasing()
							&& !repository.readDirCache().hasUnmergedPaths();
					rebaseContinueButton.setEnabled(rebaseContinueEnabled);
				} catch (IOException e) {
					Activator.handleError(
							UIText.StagingView_ReadDirCacheFailed, e,
							true);
				}

