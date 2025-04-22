					return Status.OK_STATUS;
				}
			};
			Control previousFocusControl = tree.getControl().getDisplay().getFocusControl();
			if (previousFocusControl == null) {
				previousFocusControl = rootDirectoryText;
			}
			Point initialSelection = rootDirectoryText.getSelection();
			wizardProgressMonitor.get().attachToCancelComponent(null);
			wizardProgressMonitor.get().setVisible(true);
			previousFocusControl.setFocus();
			rootDirectoryText.setSelection(initialSelection);
			ToolItem stopButton = getStopButton(wizardProgressMonitor.get());
			stopButton.addSelectionListener(this.cancelWorkListener);
			jobMonitor = ProgressManager.getInstance().progressFor(refreshProposalsJob);
			delegateMonitor = new DelegateProgressMonitorInUIThreadAndPreservingFocus(wizardProgressMonitor.get());
			jobMonitor.addProgressListener(delegateMonitor);
			refreshProposalsJob.setPriority(Job.INTERACTIVE);
			refreshProposalsJob.setUser(true);
			refreshProposalsJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					if (!tree.getControl().isDisposed()) {
						tree.getControl().getDisplay().asyncExec(() -> {
							if (!tree.getControl().isDisposed() && event.getResult().isOK()) {
								computingItem.dispose();
								if (sourceIsValid() && getWizard().getImportJob() == importJob) {
									proposalsUpdated();
								}
								tree.getTree().setEnabled(true);
							} else if (event.getResult().getCode() == IStatus.CANCEL) {
								computingItem.setText(DataTransferMessages.SmartImportProposals_inspecitionCanceled);
							} else if (event.getResult().getCode() == IStatus.ERROR) {
								computingItem.setText(
										NLS.bind(DataTransferMessages.SmartImportProposals_errorWhileInspecting,
												event.getResult().getMessage()));
							}
							if (!wizardProgressMonitor.get().isDisposed()
									&& refreshProposalsJob.getState() == Job.NONE) {
								wizardProgressMonitor.get().setVisible(false);
							}
						});
					}
				}
			});
			refreshProposalsJob.schedule(0);
		}
	}

	private static ToolItem getStopButton(ProgressMonitorPart part) {
		for (Control control : part.getChildren()) {
			if (control instanceof ToolBar) {
				for (ToolItem item : ((ToolBar) control).getItems()) {
					if (item.getToolTipText().equals(JFaceResources.getString("ProgressMonitorPart.cancelToolTip"))) { //$NON-NLS-1$ ))
						return item;
					}
				}
