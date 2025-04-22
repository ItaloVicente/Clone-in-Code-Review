		IResource[] resources = getSelectedResources(event);
		if (resources.length == 1) {
			final PreviousCommit previous = getPreviousRevision(event,
					resources[0]);
			if (previous != null) {
				IWorkbenchPage workBenchPage = HandlerUtil
						.getActiveWorkbenchWindowChecked(event).getActivePage();
