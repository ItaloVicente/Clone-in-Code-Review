	public ProgressRegion() {
	}

	@Inject
	AnimationManager animationManager;

	@Inject
	ContentProviderFactory contentProviderfactory;

	@Inject
	FinishedJobs finishedJobs;

	@PostConstruct
	public Control createContents(Composite parent) {
		GC gc = new GC(parent);
		gc.setAdvanced(true);
		forceHorizontal = !gc.getAdvanced();
		gc.dispose();

		region = new Composite(parent, SWT.NONE) {
