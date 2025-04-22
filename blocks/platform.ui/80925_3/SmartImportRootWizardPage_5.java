				wizardProgressMonitor.setVisible(true);
				currentProgressMonitor = new DelegateProgressMonitorExceptCancel(
						wizardProgressMonitor);
				ModalContext.run(refreshProposalsRunnable, true,
						currentProgressMonitor, getShell().getDisplay());
				computingItem.dispose();
				if (sourceIsValid() && getWizard().getImportJob() == importJob) {
					proposalsUpdated();
				}
				tree.getTree().setEnabled(true);
