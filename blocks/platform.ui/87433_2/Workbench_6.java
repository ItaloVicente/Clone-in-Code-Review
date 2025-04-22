				Workbench workbench = new Workbench(display, advisor, e4Workbench
						.getApplication(), e4Workbench.getContext());

				if (createSplash)
					workbench.createSplashWrapper();

				AbstractSplashHandler handler = getSplash();

				boolean showProgress = PrefUtil.getAPIPreferenceStore().getBoolean(
								IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP);

				IProgressMonitor progressMonitor = null;
				SynchronousBundleListener bundleListener = null;
				if (handler != null && showProgress) {
					progressMonitor = handler.getBundleProgressMonitor();
					if (progressMonitor != null) {
						double cutoff = 0.95;
						int expectedProgressCount = Math.max(1, WorkbenchPlugin.getDefault()
								.getBundleCount() / 10);
						progressMonitor.beginTask("", expectedProgressCount); //$NON-NLS-1$
						bundleListener = workbench.new StartupProgressBundleListener(
								progressMonitor, (int) (expectedProgressCount * cutoff));
						WorkbenchPlugin.getDefault().addBundleListener(bundleListener);
