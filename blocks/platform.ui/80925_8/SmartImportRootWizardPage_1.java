	private boolean detectNestedProjects = true;
	private boolean configureProjects = true;
	private Set<IWorkingSet> workingSets;
	private WorkingSetConfigurationBlock workingSetsBlock;
	protected ProgressMonitorPart wizardProgressMonitor;

	private Job refreshProposalsJob;

	private JobMonitor jobMonitor;

	private DelegateProgressMonitorInUIThreadAndPreservingFocus delegateMonitor;
