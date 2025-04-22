		if (repository != null && selected.length == 1
				&& selected[0] instanceof IFile)
			JobUtil.scheduleUserJob(new BlameOperation(repository,
					(IFile) selected[0], HandlerUtil.getActiveShell(event),
					HandlerUtil.getActiveSite(event).getPage()),
					UIText.ShowBlameHandler_JobName, null);
