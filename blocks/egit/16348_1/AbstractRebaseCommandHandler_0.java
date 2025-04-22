		JobUtil.scheduleUserJob(rebase, jobname, JobFamilies.REBASE,
				new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent cevent) {
						IStatus result = cevent.getJob().getResult();
						if (result.getSeverity() == IStatus.CANCEL)
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									Shell shell = PlatformUI.getWorkbench()
											.getActiveWorkbenchWindow()
											.getShell();
									MessageDialog
											.openInformation(
													shell,
													UIText.AbstractRebaseCommand_DialogTitle,
													dialogMessage);
								}
							});
						else if (result.isOK())
							RebaseResultDialog.show(rebase.getResult(),
									repository);
					}
				});
