	}

	class RefreshUiJob extends WorkbenchJob {
		volatile boolean needsNewInput;
		volatile Runnable uiTask;

		RefreshUiJob() {
			super(PlatformUI.getWorkbench().getDisplay(),
					"Refreshing Git Repositories View"); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
		}

		@Override
		public boolean belongsTo(Object family) {
			return JobFamilies.REPO_VIEW_REFRESH.equals(family);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			final boolean trace = GitTraceLocation.REPOSITORIESVIEW.isActive();
			long start = 0;
			if (trace) {
				start = System.currentTimeMillis();
				trace("Starting async update job"); //$NON-NLS-1$
			}
			CommonViewer tv = getCommonViewer();
			if (monitor.isCanceled() || !UIUtils.isUsable(tv)) {
				return Status.CANCEL_STATUS;
			}

			if (needsNewInput) {
				Object[] expanded = tv.getExpandedElements();
				tv.setInput(ResourcesPlugin.getWorkspace().getRoot());
				tv.setExpandedElements(expanded);
			} else {
				tv.refresh(true);
			}
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			IWorkbenchWindow ww = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			IViewPart part = ww == null ? null
					: ww.getActivePage().findView(IPageLayout.ID_PROP_SHEET);
			if (part instanceof PropertySheet) {
				PropertySheet sheet = (PropertySheet) part;
				IPage page = sheet.getCurrentPage();
				if (page instanceof PropertySheetPage) {
					((PropertySheetPage) page).refresh();
				}
			}
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			if (!repositories.isEmpty()) {
				layout.topControl = getCommonViewer().getControl();
			} else {
				layout.topControl = emptyArea;
			}
			emptyArea.getParent().layout(true, true);
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			Runnable task = uiTask;
			if (task != null) {
				task.run();
			}
			if (trace) {
				trace("Ending async update job after " //$NON-NLS-1$
						+ (System.currentTimeMillis() - start) + " ms"); //$NON-NLS-1$
			}
			return monitor.isCanceled() ? Status.CANCEL_STATUS
					: Status.OK_STATUS;
		}
