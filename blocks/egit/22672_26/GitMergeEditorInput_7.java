	private ISynchronizationContext prepareSynchronizationContext(
			final Repository repository, Subscriber subscriber,
			Set<ResourceMapping> allModelMappings,
			RemoteResourceMappingContext mappingContext)
			throws CoreException, OperationCanceledException,
			InterruptedException {
		final ResourceMapping[] mappings = allModelMappings
				.toArray(new ResourceMapping[allModelMappings.size()]);

		final ISynchronizationScopeManager manager = new SubscriberScopeManager(subscriber.getName(), mappings, subscriber, mappingContext, true) {
			@Override
			public ISchedulingRule getSchedulingRule() {
				return RuleUtil.getRule(repository);
