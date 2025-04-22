		Data data = getData(getSelection(event));

		if (data == null)
			return null;

		Repository repository = data.repository;
		String path = data.repositoryRelativePath;
		IStorage storage = data.storage;
		RevCommit startCommit = data.startCommit;
		Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchPage page = HandlerUtil.getActiveSite(event).getPage();
		JobUtil.scheduleUserJob(new BlameOperation(repository, storage, path,
				startCommit, shell, page), UIText.ShowBlameHandler_JobName,
				JobFamilies.BLAME);
		return null;
	}

	@Override
	public boolean isEnabled() {
		IStructuredSelection selection = getSelection();
		return getData(selection) != null;
	}

	private static Data getData(IStructuredSelection selection) {
		if (selection.size() != 1)
