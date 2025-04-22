	private boolean detectNestedProjects = true;
	private boolean configureProjects = true;
	private Set<IWorkingSet> workingSets;
	private WorkingSetConfigurationBlock workingSetsBlock;
	protected Supplier<ProgressMonitorPart> wizardProgressMonitor = new Supplier<ProgressMonitorPart>() {
		private ProgressMonitorPart progressMonitorPart;
		@Override
		public ProgressMonitorPart get() {
			if (progressMonitorPart == null) {
				try {
					getWizard().getContainer().run(false, true, monitor -> {
						if (monitor instanceof ProgressMonitorPart) {
							progressMonitorPart = (ProgressMonitorPart) monitor;
						}
					});
				} catch (InvocationTargetException ite) {
					IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
							DataTransferMessages.SmartImportWizardPage_scanProjectsFailed, ite.getCause());
					StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
				} catch (InterruptedException operationCanceled) {
				}
			}
			return progressMonitorPart;
		}
	};

	private Job refreshProposalsJob;
	private JobMonitor jobMonitor;
	private DelegateProgressMonitorInUIThreadAndPreservingFocus delegateMonitor;
	private SelectionListener cancelWorkListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			stopAndDisconnectCurrentWork();
		}
	};
