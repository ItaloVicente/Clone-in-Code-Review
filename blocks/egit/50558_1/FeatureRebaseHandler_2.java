
	private void showInteractiveRebaseView() throws PartInitException {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.showView(INTERACTIVE_REBASE_VIEW_ID);
	}

	private void openWarning(RebaseResult operationResult) {
		RebaseResult.Status status = operationResult.getStatus();
		String pluginId = Activator.getPluginId();
		MultiStatus info = new MultiStatus(pluginId, 1,
				UIText.FeatureRebaseHandler_problemsOcccurredDuringRebase, null);
		info.add(new Status(IStatus.WARNING, pluginId, NLS.bind(
				UIText.FeatureRebaseHandler_statusWas, status.name())));
		if (operationResult.getConflicts() != null && !operationResult.getConflicts().isEmpty()) {
			MultiStatus warning = createRebaseConflictWarning(operationResult);
			info.addAll(warning);
		}
		ErrorDialog.openError(null, UIText.FeatureRebaseHandler_problemsOccurred, null, info);
	}
