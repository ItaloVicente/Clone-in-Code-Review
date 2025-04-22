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
			if (monitor.isCanceled() || stagingView.isDisposed())
				return Status.CANCEL_STATUS;
			try {
				SubmoduleWalk walk = SubmoduleWalk.forIndex(repo);
				boolean refresh = false;
				while (!monitor.isCanceled() && walk.next()) {
					for (StagingEntry entry : nodes) {
						if (entry.getPath().equals(walk.getPath())) {
							entry.setSubmodule(true);
							refresh = true;
						}
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

