		final Job job = new Job(

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.run(monitor);
					final PushOperationResult res = op.getOperationResult();
					if (shell != null) {
						PlatformUI.getWorkbench().getDisplay().asyncExec(
								new Runnable() {
									public void run() {
										final Dialog dialog = new PushResultDialog(
												shell, repository, res,
												repository.getDirectory()
														.getParentFile()
														.getName()
										dialog.open();
									}
								});
					}
					return Status.OK_STATUS;
				} catch (InvocationTargetException e) {
					return new Status(IStatus.ERROR, Activator.getPluginId(), e
							.getCause().getMessage(), e);
