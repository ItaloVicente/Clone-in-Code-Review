			IJobManager jobMan = Job.getJobManager();
			jobMan.join(JobFamilies.GITFLOW_FAMILY, null);

			MergeResult mergeResult = releaseFinishOperation.getMergeResult();
			MergeStatus mergeStatus = mergeResult.getMergeStatus();
			if (!MergeStatus.CONFLICTING.equals(mergeStatus)) {
				return null;
			}
			if (handleConflictsOnMaster(gfRepo)) {
				return null;
			}
			MultiStatus warning = createConflictWarning(develop, releaseBranch, mergeResult);
			ErrorDialog.openError(null, UIText.ReleaseFinishHandler_Conflicts, null, warning);
		} catch (WrongGitFlowStateException | CoreException | IOException
				| OperationCanceledException | InterruptedException e) {
