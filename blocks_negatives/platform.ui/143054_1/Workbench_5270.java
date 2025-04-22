				workbench.createSplashWrapper();

				AbstractSplashHandler handler = getSplash();

				boolean showProgress = PrefUtil.getAPIPreferenceStore().getBoolean(
								IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP);

				IProgressMonitor progressMonitor = null;
