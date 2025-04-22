		if (repository == null)
			return null;

		RepositoryMapping mapping = RepositoryMapping.getMapping(selected[0]
				.getProject());
		if (mapping == null)
			return null;

		String path = mapping.getRepoRelativePath(selected[0]);
		IStorage storage = (IStorage) selected[0];
		Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchPage page = HandlerUtil.getActiveSite(event).getPage();
		JobUtil.scheduleUserJob(new BlameOperation(repository, storage, path,
				null, shell, page), UIText.ShowBlameHandler_JobName, null);
