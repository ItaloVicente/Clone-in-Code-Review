		if (shouldRunInBackground()) {
			return;
		}

		final ProgressMonitorFocusJobDialog dialog = new ProgressMonitorFocusJobDialog(
		        shell, this, progressManager, contentProviderFactory,
		        finishedJobs);
		dialog.show(job, shell);
