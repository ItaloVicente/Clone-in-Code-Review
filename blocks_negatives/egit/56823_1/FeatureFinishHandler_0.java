					JobFamilies.GITFLOW_FAMILY);
			IJobManager jobMan = Job.getJobManager();
			jobMan.join(JobFamilies.GITFLOW_FAMILY, null);

			MergeResult mergeResult = operation.getMergeResult();

			if (squash && mergeResult.getMergedCommits().length > 1) {
				rewordCommitMessage(activeShell, gfRepo);
			}

			MergeStatus mergeStatus = mergeResult.getMergeStatus();
			if (MergeStatus.CONFLICTING.equals(mergeStatus)) {
				MultiStatus status = createMergeConflictInfo(develop, featureBranch, mergeResult);
				ErrorDialog.openError(null, UIText.FeatureFinishHandler_Conflicts, null, status);
			}
