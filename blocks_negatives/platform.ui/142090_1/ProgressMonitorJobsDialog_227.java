	        IProgressService progressService, ProgressManager progressManager,
	        ContentProviderFactory contentProviderFactory, FinishedJobs finishedJobs) {
        super(parent);
        this.progressService = progressService;
        this.progressManager = progressManager;
        this.contentProviderFactory = contentProviderFactory;
        this.finishedJobs = finishedJobs;
    }

    @Override
