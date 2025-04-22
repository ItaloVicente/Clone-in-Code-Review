		});
		ISelectionService srv = (ISelectionService) getSite().getService(
				ISelectionService.class);
		srv.addPostSelectionListener(selectionChangedListener);

		autoRefreshJob = new Job("Git Repositories View Auto-Refresh") { //$NON-NLS-1$
