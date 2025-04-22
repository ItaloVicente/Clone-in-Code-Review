		IHistoryView part;
		try {
			part = (IHistoryView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.showView(IHistoryView.VIEW_ID);
		} catch (PartInitException e) {
			Activator.logError(e.getLocalizedMessage(), e);
			return;
		}

