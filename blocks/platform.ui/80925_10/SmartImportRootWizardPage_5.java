		stopAndDisconnectCurrentWork();
		SmartImportRootWizardPage.this.potentialProjects = Collections.emptyMap();
		SmartImportRootWizardPage.this.notAlreadyExistingProjects = Collections.emptySet();
		SmartImportRootWizardPage.this.alreadyExistingProjects = Collections.emptySet();
		proposalsUpdated();
		if (sourceIsValid()) {
			Control previousFocusControl = tree.getControl().getDisplay().getFocusControl();
			if (previousFocusControl == null) {
				previousFocusControl = rootDirectoryText;
			}
			tree.getControl().setEnabled(false);
			Point initialSelection = rootDirectoryText.getSelection();
			TreeItem computingItem = new TreeItem(tree.getTree(), SWT.DEFAULT);
			computingItem
					.setText(NLS.bind(DataTransferMessages.SmartImportJob_inspecting, selection.getAbsolutePath()));
			final SmartImportJob importJob = getWizard().getImportJob();
			refreshProposalsJob = new Job(
					NLS.bind(DataTransferMessages.SmartImportJob_inspecting, selection.getAbsolutePath())) {
				@Override
				public IStatus run(IProgressMonitor monitor) {
					SmartImportRootWizardPage.this.potentialProjects = importJob.getImportProposals(monitor);
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					if (!potentialProjects.containsKey(importJob.getRoot())) {
						potentialProjects.put(importJob.getRoot(), Collections.emptyList());
					}
