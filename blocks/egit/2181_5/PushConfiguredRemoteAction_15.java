	public void done(IJobChangeEvent event) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				final Dialog dialog = new PushResultDialog(PlatformUI
						.getWorkbench().getDisplay().getActiveShell(), localDb,
						getOperationResult(), Activator.getDefault()
								.getRepositoryUtil().getRepositoryName(localDb)
								+ " - " + rc.getName()); //$NON-NLS-1$
				dialog.open();
			}
		});
	}
