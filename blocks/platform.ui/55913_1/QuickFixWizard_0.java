		IRunnableWithProgress finishRunnable = mon -> {
			IWizardPage[] pages = getPages();
			SubMonitor subMonitor = SubMonitor.convert(mon, MarkerMessages.MarkerResolutionDialog_Fixing,
					(10 * pages.length) + 1);
			subMonitor.worked(1);
			for (int i = 0; i < pages.length; i++) {
				getShell().getDisplay().readAndDispatch();
				if (subMonitor.isCanceled())
					return;
				QuickFixPage wizardPage = (QuickFixPage) pages[i];
				wizardPage.performFinish(subMonitor.newChild(10));
			}
		};
