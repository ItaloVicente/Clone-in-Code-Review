			String repoName = Activator.getDefault().getRepositoryUtil()
					.getRepositoryName(repository);

			String jobName = NLS.bind(UIText.PullOperationUI_PullingTaskName,
					shortBranchName, repoName);
			JobUtil.scheduleUserJob(this, jobName, JobFamilies.PULL, this);

		} finally {
			if (errorMessage != null)
				MessageDialog.openError(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						UIText.PullOperationUI_PullErrorWindowTitle,
						errorMessage);
		}
