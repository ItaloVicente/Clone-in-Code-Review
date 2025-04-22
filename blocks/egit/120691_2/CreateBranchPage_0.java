	private void createValidateJob() {
		validateJob = new WorkbenchJob("Validate branch name") {//$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (sourceNameLabel.isDisposed()) {
					return Status.CANCEL_STATUS;
				}
				checkPage();
				return Status.OK_STATUS;
			}
		};
		validateJob.setSystem(true);
	}

