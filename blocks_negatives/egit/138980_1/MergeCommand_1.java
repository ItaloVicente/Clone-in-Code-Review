		String jobname = NLS.bind(UIText.MergeAction_JobNameMerge, refName);
		Job job = new WorkspaceJob(jobname) {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (final CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent jobEvent) {
				IStatus result = jobEvent.getJob().getResult();
				if (result.getSeverity() == IStatus.CANCEL)
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							MessageDialog.openInformation(shell,
									UIText.MergeAction_MergeCanceledTitle,
									UIText.MergeAction_MergeCanceledMessage);
						}
					});
				else if (!result.isOK())
					Activator.handleError(result.getMessage(), result
							.getException(), true);
				else
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							MergeResultDialog.getDialog(shell, repository, op.getResult()).open();
						}
					});
			}
		});
		job.schedule();

