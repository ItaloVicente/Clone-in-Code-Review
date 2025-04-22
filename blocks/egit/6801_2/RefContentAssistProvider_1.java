		this.pushMode = pushMode;
	}

	public Job fetchPushDestinationRefsForContentAssist() {
		if (fetchJob != null)
			return fetchJob;

		fetchJob= new FetchPushDestinationRefsJob();
		fetchJob.schedule();
		return fetchJob;
