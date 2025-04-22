		IWorkbenchPage workBenchPage = HandlerUtil
				.getActiveWorkbenchWindowChecked(event).getActivePage();
		try {
			CompareUtils.compare(resources, repo, Constants.HEAD, commitId,
					true, workBenchPage);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
