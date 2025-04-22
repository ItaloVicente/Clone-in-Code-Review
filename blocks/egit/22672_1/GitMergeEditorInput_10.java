
	private static class GitSynchronizationContext extends
			SubscriberMergeContext {
		public GitSynchronizationContext(Subscriber subscriber,
				ISynchronizationScopeManager scopeManager) {
			super(subscriber, scopeManager);
			initialize();
		}

		public void markAsMerged(IDiff node, boolean inSyncHint,
				IProgressMonitor monitor) throws CoreException {
		}

		public void reject(IDiff diff, IProgressMonitor monitor)
				throws CoreException {
		}

		@Override
		protected void makeInSync(IDiff diff, IProgressMonitor monitor)
				throws CoreException {
		}
	}
