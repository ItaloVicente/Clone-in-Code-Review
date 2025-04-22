		final RebaseOperation rebase = new RebaseOperation(repository, ref);
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					rebase.execute(monitor);
				} catch (final CoreException e) {
					try {
						new RebaseOperation(repository, Operation.ABORT)
								.execute(monitor);
					} catch (CoreException e1) {
						return e1.getStatus();
					}
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(rebase.getSchedulingRule());
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
											UIText.RebaseCurrentRefCommand_RebaseCanceledTitle,
											UIText.RebaseCurrentRefCommand_RebaseCanceledMessage);
						}
					});
				} else if (result.isOK()) {
					RebaseResultDialog.show(rebase.getResult(), repository);
				}
			}
		});
		job.schedule();
