
	public static void doMerge(Repository repository, MergeOperation op,
			String refName) {
		JobUtil.scheduleUserWorkspaceJob(op,
				NLS.bind(UIText.MergeAction_JobNameMerge, refName),
				JobFamilies.MERGE, new JobChangeAdapter() {

					@Override
					public void done(IJobChangeEvent event) {
						IStatus result = event.getJob().getResult();
						if (result.getSeverity() == IStatus.CANCEL) {
							PlatformUI.getWorkbench().getDisplay()
									.asyncExec(() -> {
										Shell shell = PlatformUI.getWorkbench()
												.getActiveWorkbenchWindow()
												.getShell();
										MessageDialog.openInformation(shell,
												UIText.MergeAction_MergeCanceledTitle,
												UIText.MergeAction_MergeCanceledMessage);
									});
						} else if (!result.isOK()) {
							Activator.handleError(result.getMessage(),
									result.getException(), true);
						} else {
							PlatformUI.getWorkbench().getDisplay()
									.asyncExec(() -> {
										Shell shell = PlatformUI.getWorkbench()
												.getActiveWorkbenchWindow()
												.getShell();
										MergeResultDialog.getDialog(shell,
												repository, op.getResult())
												.open();
									});
						}
					}
				});
	}
