	@Inject
	ProgressManager progressManager;

	@PostConstruct
	void init(MApplication application) {
		progressManager.addListener(listener);
		application.getContext().set(FinishedJobs.class, this);
	}

