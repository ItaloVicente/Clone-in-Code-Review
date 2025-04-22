	private static class RepositoryChangeScanner extends Job
			implements IPropertyChangeListener {

		private volatile boolean doReschedule;

		private int interval;

		private final RepositoryCache repositoryCache;

