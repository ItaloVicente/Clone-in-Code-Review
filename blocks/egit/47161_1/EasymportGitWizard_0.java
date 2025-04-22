		EasymportJob job = new EasymportJob(this.selectRootPage.getSelectedRootDirectory(), this.selectRootPage.getSelectedWorkingSets(), this.selectRootPage.isConfigureAndDetectNestedProject());
		EasymportJobReportDialog dialog = new EasymportJobReportDialog(getShell(), job);
		job.schedule();
		if (this.selectRootPage.isConfigureAndDetectNestedProject()) {
			dialog.open();
		}
