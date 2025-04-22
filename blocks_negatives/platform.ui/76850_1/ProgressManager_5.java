	@Override
	public void showInDialog(Shell shell, Job job) {
		if (shouldRunInBackground()) {
			return;
		}

		final ProgressMonitorFocusJobDialog dialog = new ProgressMonitorFocusJobDialog(
				shell);
		dialog.show(job, shell);
	}
