
		String branchName;
		try {
			branchName = repository.getBranch();
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		}

		String jobname = NLS.bind(
				UIText.PullCurrentBranchActionHandler_PullJobname, branchName);
		int timeout = Activator.getDefault().getPreferenceStore().getInt(
				UIPreferences.REMOTE_CONNECTION_TIMEOUT);
		final PullOperation pull = new PullOperation(repository, timeout);
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					pull.execute(monitor);
				} catch (final CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(pull.getSchedulingRule());
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent cevent) {
				IStatus result = cevent.getJob().getResult();
				if (result.getSeverity() == IStatus.CANCEL) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							MessageDialog
									.openInformation(
											shell,
											UIText.PullCurrentBranchActionHandler_PullCanceledTitle,
											UIText.PullCurrentBranchActionHandler_PullCanceledMessage);
						}
					});
				} else if (result.isOK()) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							new PullResultDialog(shell, repository, pull
									.getResult()).open();
						}
					});
				}
			}
		});
		job.schedule();
