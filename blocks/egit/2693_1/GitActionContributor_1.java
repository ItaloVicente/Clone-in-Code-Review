
		ISynchronizePageSite site = configuration.getSite();
		IWorkbenchSite ws = site.getWorkbenchSite();
		openFileAction = new OpenFileAction(ws.getWorkbenchWindow()
			.getActivePage());

		site.getSelectionProvider().addSelectionChangedListener(
			openFileAction);
