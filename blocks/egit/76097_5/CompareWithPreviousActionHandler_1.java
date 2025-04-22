		if (resources != null && resources.length > 0) {
			try {
				IWorkbenchPage workBenchPage = HandlerUtil
						.getActiveWorkbenchWindowChecked(event).getActivePage();
				final RevCommit previous = getPreviousRevision(event,
						resources);
				if (previous != null) {
					CompareUtils.compare(resources, repository, Constants.HEAD,
							previous.getName(), true, workBenchPage);
				} else {
					showNotFoundDialog(event, resources);
				}
			} catch (Exception e) {
				Activator.handleError(
						UIText.CompareWithRefAction_errorOnSynchronize, e,
						true);
