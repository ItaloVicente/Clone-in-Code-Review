		if (ws instanceof IViewSite) {
			openWorkingFileAction = new OpenWorkingFileAction(ws.getWorkbenchWindow()
					.getActivePage());
			site.getSelectionProvider().addSelectionChangedListener(
					openWorkingFileAction);
		}
