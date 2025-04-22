		try {
			discardChanges();
		} catch (GitAPIException e) {
			errorOccurred = true;
			Activator.logError(CoreText.DiscardChangesOperation_discardFailed, e);
		}
		monitor.worked(1);
		try {
			ProjectUtil.refreshResources(files, new SubProgressMonitor(monitor,
					1));
		} catch (CoreException e) {
			errorOccurred = true;
			Activator.logError(CoreText.DiscardChangesOperation_refreshFailed,
					e);
