	public JGitEventsBroadcast(ClusterMessageService clusterMessageService
			Consumer<WatchEventsWrapper> eventsPublisher) {
		this.clusterMessageService = clusterMessageService;
		this.eventsPublisher = eventsPublisher;
		setupJMSConnection();
	}
