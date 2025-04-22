		String uriText = uriCombo.getText();
		if (!changeRefs.containsKey(uriText)) {
			changeRefs.put(uriText, new ChangeList(repository, uriText));
		}
		ChangeList list = changeRefs.get(uriText);
		if (!list.isDone()) {
			IWizardContainer container = getContainer();
			IRunnableWithProgress operation = monitor -> {
				monitor.beginTask(MessageFormat.format(
						UIText.FetchGerritChangePage_FetchingRemoteRefsMessage,
						uriText), IProgressMonitor.UNKNOWN);
				List<Change> result = list.get();
				if (monitor.isCanceled()) {
					return;
				}
				if (result == null || result.isEmpty()) {
					return;
				}
				Job showProposals = new WorkbenchJob(
						UIText.FetchGerritChangePage_ShowingProposalsJobName) {

					@Override
					public IStatus runInUIThread(IProgressMonitor uiMonitor) {
						try {
							if (container instanceof NonBlockingWizardDialog) {
								if (refText != refText.getDisplay()
										.getFocusControl()) {
									return Status.CANCEL_STATUS;
								}
								String uriNow = uriCombo.getText();
								if (!uriNow.equals(uriText)) {
									return Status.CANCEL_STATUS;
								}
