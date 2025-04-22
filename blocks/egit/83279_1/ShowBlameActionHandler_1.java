			Shell shell = HandlerUtil.getActiveShell(event);
			IWorkbenchPage page = HandlerUtil.getActiveSite(event).getPage();
			JobUtil.scheduleUserJob(
					new BlameOperation((CommitFileRevision) element, shell,
							page),
					UIText.ShowBlameHandler_JobName, JobFamilies.BLAME);
