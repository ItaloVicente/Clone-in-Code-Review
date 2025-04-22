	private void postMerge(final GitFlowRepository gfRepo,
			final String featureBranch, final boolean squash,
			final MergeResult mergeResult) {
		Display display = Display.getDefault();

		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				Shell activeShell = Display.getDefault().getActiveShell();

				if (squash && mergeResult.getMergedCommits().length > 1) {
					try {
						rewordCommitMessage(activeShell, gfRepo);
					} catch (CoreException | IOException e) {
						throw new RuntimeException(e);
					}
				}

				MergeStatus mergeStatus = mergeResult.getMergeStatus();
				if (MergeStatus.CONFLICTING.equals(mergeStatus)) {
					String develop = gfRepo.getConfig().getDevelop();
					MultiStatus status = createMergeConflictInfo(develop,
							featureBranch, mergeResult);
					ErrorDialog.openError(null, UIText.FeatureFinishHandler_Conflicts,
							null, status);
				}
			}
		});

	}

