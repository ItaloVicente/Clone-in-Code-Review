			IJobManager jobMan = Job.getJobManager();
			jobMan.join(JobFamilies.GITFLOW_FAMILY, null);

			MergeResult mergeResult = operation.getMergeResult();
			MergeStatus mergeStatus = mergeResult.getMergeStatus();
			if (MergeStatus.CONFLICTING.equals(mergeStatus)) {
				MultiStatus warning = createConflictWarning(develop, featureBranch, mergeResult);
				ErrorDialog.openError(null, UIText.FeatureFinishHandler_Conflicts, null, warning);
			}
		} catch (WrongGitFlowStateException | CoreException | IOException
				| OperationCanceledException | InterruptedException e) {
