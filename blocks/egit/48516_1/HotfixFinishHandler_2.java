			IJobManager jobMan = Job.getJobManager();
			jobMan.join(JobFamilies.GITFLOW_FAMILY, null);

			MergeStatus mergeResult = hotfixFinishOperation.getMergeResult();
			if (!MergeStatus.CONFLICTING.equals(mergeResult)) {
				return null;
			}
			if (handleConflictsOnMaster(gfRepo)) {
				return null;
			}
			handleConflictsOnDevelop();
		} catch (WrongGitFlowStateException | CoreException | IOException
				| OperationCanceledException | InterruptedException e) {
