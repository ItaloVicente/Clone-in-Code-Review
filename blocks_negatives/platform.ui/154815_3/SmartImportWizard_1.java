		if (!runInBackground) {
			if (projectRootPage.isDetectNestedProject() || projectRootPage.isConfigureProjects()) {
				SmartImportJobReportDialog dialog = new SmartImportJobReportDialog(null);
				dialog.setBlockOnOpen(false);
				getContainer().getShell().setEnabled(false);
				dialog.show(job, getShell());
			}
		}
