
		for (IWorkbenchAction action : actions.actionsToDispose)
			action.dispose();
		actions.actionsToDispose.clear();
		cancelRefreshJob();
		if (popupMgr != null) {
			for (final IContributionItem i : popupMgr.getItems()) {
				if (i instanceof IWorkbenchAction)
					((IWorkbenchAction) i).dispose();
			}
			for (final IContributionItem i : getSite().getActionBars()
					.getMenuManager().getItems()) {
				if (i instanceof IWorkbenchAction)
					((IWorkbenchAction) i).dispose();
