			IJobManager jobMan = Job.getJobManager();
			jobMan.join(JobFamilies.GITFLOW_FAMILY, null);

			MergeStatus mergeResult = operation.getMergeResult();
			if (MergeStatus.CONFLICTING.equals(mergeResult)) {
				MessageDialog.openWarning(null,
						UIText.FeatureFinishHandler_Conflicts,
						UIText.FeatureFinishHandler_featureFinishConflicts);
			}
		} catch (WrongGitFlowStateException | CoreException | IOException
				| OperationCanceledException | InterruptedException e) {
