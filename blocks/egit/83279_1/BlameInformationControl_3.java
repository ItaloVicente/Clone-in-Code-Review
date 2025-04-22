			if (rev instanceof CommitFileRevision) {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				BlameOperation operation = new BlameOperation(
						(CommitFileRevision) rev, getShell(),
						page, line);
				JobUtil.scheduleUserJob(operation,
						UIText.ShowBlameHandler_JobName, JobFamilies.BLAME);
			}
