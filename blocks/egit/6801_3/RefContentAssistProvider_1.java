		this.pushMode = pushMode;
	}

	public Job preFetchPushDestinationRefs() {
		if (fetchJob != null)
			return fetchJob;

		fetchJob= new FetchPushDestinationRefsJob();
		fetchJob.schedule();
		return fetchJob;
