		IWorkbenchPage workBenchPage = HandlerUtil
				.getActiveWorkbenchWindowChecked(event).getActivePage();
		try {
			CompareUtils.compare(resources, repository, Constants.HEAD,
					Constants.HEAD, true, workBenchPage);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
