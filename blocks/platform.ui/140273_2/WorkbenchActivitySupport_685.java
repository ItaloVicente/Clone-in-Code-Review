		tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(getActivitySupportExtensionPoint()));
		mutableActivityManager = new MutableActivityManager(getTriggerPointAdvisor());
		proxyActivityManager = new ProxyActivityManager(mutableActivityManager);
		mutableActivityManager.addActivityManagerListener(new IActivityManagerListener() {

			private Set<String> lastEnabled = new HashSet<>(mutableActivityManager.getEnabledActivityIds());

			@Override
			public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
				Set<String> activityIds = mutableActivityManager.getEnabledActivityIds();
				if (!activityIds.equals(lastEnabled)) {
					lastEnabled = new HashSet<>(activityIds);

					if (!PlatformUI.isWorkbenchRunning()) {
						return;
					}

					final IWorkbench workbench = PlatformUI.getWorkbench();
					IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
					for (IWorkbenchWindow wWindow : windows) {
						if (wWindow instanceof WorkbenchWindow) {
							final WorkbenchWindow window = (WorkbenchWindow) wWindow;

							final ProgressMonitorDialog dialog = new ProgressMonitorDialog(window.getShell());

							final IRunnableWithProgress runnable = new IRunnableWithProgress() {

								private long openTime;

								private boolean dialogOpened = false;

								@Override
								public void run(IProgressMonitor monitor) {

									openTime = System.currentTimeMillis()
											+ workbench.getProgressService().getLongOperationTime();

									monitor.beginTask(ActivityMessages.ManagerTask, 2);

									monitor.subTask(ActivityMessages.ManagerWindowSubTask);

									updateWindowBars(window);
									monitor.worked(1);

									monitor.subTask(ActivityMessages.ManagerViewsSubTask);
									IWorkbenchPage[] pages = window.getPages();
									for (IWorkbenchPage page : pages) {
										IViewReference[] refs = page.getViewReferences();
										for (IViewReference ref : refs) {
											IViewPart part = ref.getView(false);
											if (part != null) {
												updateViewBars(part);
