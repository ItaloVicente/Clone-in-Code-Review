		if (selection.isEmpty())
			return null;

		RevCommit commit = (RevCommit) selection.getFirstElement();
		Object input = getPage().getInputInternal().getSingleFile();
		Repository repository = getRepository(event);

		try {
			IWorkbenchPage workBenchPage = HandlerUtil
					.getActiveWorkbenchWindowChecked(event).getActivePage();
