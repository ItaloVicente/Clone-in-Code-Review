
	private static RemoteResourceMappingContext prepareContext(
			Repository repository, String leftRev, String rightRev,
			boolean includeLocal) throws IOException {
		GitSynchronizeData gsd = new GitSynchronizeData(repository, leftRev,
				rightRev, includeLocal);
		GitSynchronizeDataSet gsds = new GitSynchronizeDataSet(gsd);
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsds);
		subscriber.init(new NullProgressMonitor());

		return new GitSubscriberResourceMappingContext(subscriber, gsds);
	}
