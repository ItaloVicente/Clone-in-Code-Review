		if (shouldRunInBackground()) {
			return;
		}

		final ProgressMonitorFocusJobDialog dialog = new ProgressMonitorFocusJobDialog(
				shell);
		dialog.show(job, shell);
