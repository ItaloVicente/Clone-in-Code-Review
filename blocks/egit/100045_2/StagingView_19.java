	private void asyncUpdate(Runnable runnable) {
		Job update = new WorkbenchJob("Foo") { //$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					runnable.run();
					return Status.OK_STATUS;
				} catch (Exception e) {
					return Activator.createErrorStatus(e.getLocalizedMessage(),
							e);
				}
			}

			@Override
			public boolean belongsTo(Object family) {
				return family == JobFamilies.STAGING_VIEW_RELOAD
						|| super.belongsTo(family);
			}
		};
		update.setSystem(true);
		update.schedule();
	}

