		IRunnableWithProgress finishRunnable = monitor -> {
		IWizardPage[] pages = getPages();
		monitor.beginTask(MarkerMessages.MarkerResolutionDialog_Fixing,
				(10 * pages.length) + 1);
		monitor.worked(1);
		for (int i = 0; i < pages.length; i++) {
			getShell().getDisplay().readAndDispatch();
			if(monitor.isCanceled())
				return;
			QuickFixPage wizardPage = (QuickFixPage) pages[i];
			wizardPage.performFinish(new SubProgressMonitor(monitor,10));
			monitor.worked(1);
		}
		monitor.done();

};
