				rootDirectoryText.setFocus();
				DelegateProgressMonitorPreservingFocus delegateMonitor = new DelegateProgressMonitorPreservingFocus(
						wizardProgressMonitor, getShell());
				ModalContext.run(refreshProposalsRunnable, true,
						delegateMonitor, getShell().getDisplay());
				computingItem.dispose();
				if (sourceIsValid() && getWizard().getImportJob() == importJob) {
					proposalsUpdated();
				}
				tree.getTree().setEnabled(true);
