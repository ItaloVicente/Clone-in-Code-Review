		MessageDialog.openWarning(null,
				UIText.FeatureRebaseHandler_conflicts,
				UIText.FeatureRebaseHandler_resolveConflictsManually);
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(INTERACTIVE_REBASE_VIEW_ID);
		} catch (PartInitException e) {
			return error(e.getMessage(), e);
