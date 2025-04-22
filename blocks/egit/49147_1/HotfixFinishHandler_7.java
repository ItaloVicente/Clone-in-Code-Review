			IJobManager jobMan = Job.getJobManager();
			jobMan.join(JobFamilies.GITFLOW_FAMILY, null);

			MergeResult mergeResult = hotfixFinishOperation.getMergeResult();
			MergeStatus mergeStatus = mergeResult.getMergeStatus();
			if (!MergeStatus.CONFLICTING.equals(mergeStatus)) {
				return null;
			}
			if (handleConflictsOnMaster(gfRepo)) {
				return null;
			}
			MultiStatus warning = createConflictWarning(develop, hotfixBranch, mergeResult);
			ErrorDialog.openError(null, UIText.HotfixFinishHandler_Conflicts, null, warning);
		} catch (WrongGitFlowStateException | CoreException | IOException
				| OperationCanceledException | InterruptedException e) {
