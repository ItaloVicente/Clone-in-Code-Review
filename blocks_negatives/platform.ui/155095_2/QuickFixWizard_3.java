		IRunnableWithProgress finishRunnable = mon -> {
			IWizardPage[] pages = getPages();
			SubMonitor subMonitor = SubMonitor.convert(mon, MarkerMessages.MarkerResolutionDialog_Fixing,
					(10 * pages.length) + 1);
			subMonitor.worked(1);
			for (IWizardPage page : pages) {
				getShell().getDisplay().readAndDispatch();
				QuickFixPage wizardPage = (QuickFixPage) page;
				wizardPage.performFinish(subMonitor.split(10));
			}
		};

