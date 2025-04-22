		boolean runInBackground = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IPreferenceConstants.RUN_IN_BACKGROUND);
		job.setProperty(IProgressConstants.PROPERTY_IN_DIALOG, runInBackground);
		if (!runInBackground) {
			if (projectRootPage.isDetectNestedProject() || projectRootPage.isConfigureProjects()) {
				SmartImportJobReportDialog dialog = new SmartImportJobReportDialog(null);
				dialog.setBlockOnOpen(false);
				getContainer().getShell().setEnabled(false);
				dialog.show(job, getShell());
			}
