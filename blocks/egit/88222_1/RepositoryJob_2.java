
	private void showResultDeferred(final IAction action) {
		WorkbenchJob dialogJob = new WorkbenchJob(action.getText()) {

			private boolean isModal(Shell shell) {
				return (shell.getStyle() & (SWT.APPLICATION_MODAL
						| SWT.PRIMARY_MODAL | SWT.SYSTEM_MODAL)) != 0;
			}

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				Shell shell = PlatformUI.getWorkbench()
						.getModalDialogShellProvider().getShell();
				if (shell == null) {
					return Status.CANCEL_STATUS;
				}
				if (isModal(shell)) {
					schedule(PlatformUI.getWorkbench().getProgressService()
							.getLongOperationTime());
					return Status.CANCEL_STATUS;
				}
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				action.run();
				return Status.OK_STATUS;
			}
		};
		dialogJob.setSystem(true);
		dialogJob.schedule();
	}
