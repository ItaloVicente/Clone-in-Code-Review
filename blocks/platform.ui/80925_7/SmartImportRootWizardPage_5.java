				rootDirectoryText.setFocus();
				delegateMonitor = new DelegateProgressMonitorInUIThreadAndPreservingFocus(
						wizardProgressMonitor, getShell());
				refreshProposalsJob.setPriority(Job.INTERACTIVE);
				refreshProposalsJob.setUser(true);
				refreshProposalsJob.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent event) {
						if (!tree.getControl().isDisposed()) {
							tree.getControl().getDisplay().asyncExec(() -> {
								if (event.getResult().isOK()) {
									computingItem.dispose();
									if (sourceIsValid() && getWizard().getImportJob() == importJob) {
										proposalsUpdated();
									}
									tree.getTree().setEnabled(true);
								} else if (event.getResult().getCode() == IStatus.CANCEL) {
									computingItem
											.setText(DataTransferMessages.SmartImportProposals_inspecitionCanceled);
								} else if (event.getResult().getCode() == IStatus.ERROR) {
									computingItem.setText(
											NLS.bind(DataTransferMessages.SmartImportProposals_errorWhileInspecting,
											event.getResult().getMessage()));
								}
								if (!wizardProgressMonitor.isDisposed()) {
									wizardProgressMonitor.setVisible(false);
								}
							});
						}
					}
				});
				jobMonitor.addProgressListener(delegateMonitor);
				stopButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						stopAndDisconnectCurrentWork();
					}
				});
				refreshProposalsJob.schedule(0);
