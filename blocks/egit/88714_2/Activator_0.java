	static class RepositoryChangeScanner extends Job
			implements IPropertyChangeListener {

		volatile boolean doReschedule;

		private int interval;

