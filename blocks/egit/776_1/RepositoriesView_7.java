		autoRefreshJob = new Job("Git Repositories View Auto-Refresh") { //$NON-NLS-1$

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				scheduleRefresh();
				schedule(AUTO_REFRESH_INTERVAL_MILLISECONDS);
				return Status.OK_STATUS;
			}
		};

		autoRefreshJob.setSystem(true);
		autoRefreshJob.schedule(AUTO_REFRESH_INTERVAL_MILLISECONDS);
