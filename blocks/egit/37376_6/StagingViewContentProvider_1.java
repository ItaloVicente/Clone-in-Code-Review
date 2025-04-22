	private static class SubmoduleWalkJob extends Job {

		private Set<StagingEntry> nodes;

		private Repository repo;

		private final StagingView stagingView;

		private final Object family;

		SubmoduleWalkJob(String name, Repository repository,
				Set<StagingEntry> nodes, StagingView stagingView, Object family) {
			super(name);
			this.nodes = nodes;
			this.repo = repository;
			this.family = family;
			this.stagingView = stagingView;
			setRule(RuleUtil.getRule(repository));
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				SubmoduleWalk walk = SubmoduleWalk.forIndex(repo);
				boolean refresh = false;
				while (!monitor.isCanceled() && walk.next()) {
					for (StagingEntry entry : nodes) {
						boolean isSubmodule = entry.getPath().equals(
								walk.getPath());
						entry.setSubmodule(isSubmodule);
						if (isSubmodule)
							refresh = true;
					}
				}
				if (refresh && !stagingView.isDisposed())
					stagingView.refreshViewersPreservingExpandedElements();
			} catch (IOException e) {
				return Activator.createErrorStatus(
						UIText.StagingViewContentProvider_SubmoduleError, e);
			}
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
			return Status.OK_STATUS;
		}

		@Override
		public boolean belongsTo(Object other) {
			return this.family == other || super.belongsTo(other);
		}
	}

