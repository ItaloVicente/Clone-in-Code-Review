		if (background) {
			final Job job = new Job(NLS.bind(UIText.GitCloneWizard_jobName, uri
					.toString())) {
				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					try {
						op.run(monitor);
						cloneSource.saveUriInPrefs();
						config.addConfiguredRepository(op.getGitDir());
						return Status.OK_STATUS;
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					} catch (InvocationTargetException e) {
						Throwable thr = e.getCause();
						return new Status(IStatus.ERROR, Activator
								.getPluginId(), 0, thr.getMessage(), thr);
					}
