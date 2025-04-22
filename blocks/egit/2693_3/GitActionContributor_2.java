
		ISynchronizePageSite site = configuration.getSite();
		IWorkbenchSite ws = site.getWorkbenchSite();
		openWorkingFileAction = new OpenWorkingFileAction(ws.getWorkbenchWindow()
			.getActivePage());

		site.getSelectionProvider().addSelectionChangedListener(
				openWorkingFileAction);
