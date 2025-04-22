				createSplash = WorkbenchPlugin.isSplashHandleSpecified();
				if (createSplash) {

					workbench.createSplashWrapper();

					AbstractSplashHandler handler = getSplash();
					if (handler != null) {
						Shell splashShell = handler.getSplash();
						if (splashShell != null && !splashShell.isDisposed()) {
							splashShell.setVisible(true);
							splashShell.forceActive();
						}
					}
					spinEventQueueToUpdateSplash(display);

					if (handler != null && showProgress) {
						IProgressMonitor progressMonitor = SubMonitor.convert(handler.getBundleProgressMonitor());
						bundleListener = new Workbench.StartupProgressBundleListener(progressMonitor, display);
