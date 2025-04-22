	private static class SubmoduleWalkJob extends Job {
		private Set<StagingEntry> nodes;

		private Repository repo;

		private final StagingView stagingView;

		SubmoduleWalkJob(String name, Repository repository,
				Set<StagingEntry> nodes, StagingView stagingView) {
			super(name);
			this.nodes = nodes;
			this.repo = repository;
			this.stagingView = stagingView;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IStatus status = Status.OK_STATUS;
			try {
				SubmoduleWalk walk = SubmoduleWalk.forIndex(repo);
				boolean refresh = false;
				while (walk.next() && !monitor.isCanceled()) {
					for (StagingEntry entry : nodes) {
						boolean isSubmodule = entry.getPath().equals(
								walk.getPath());
						entry.setSubmodule(isSubmodule);
						if (isSubmodule)
							refresh = true;
					}
				}
				if (refresh)
					stagingView.refreshViewersPreservingExpandedElements();
			} catch (IOException e) {
				status = Activator.createErrorStatus(
						UIText.StagingViewContentProvider_SubmoduleError, e);
			}
			return status;
		}
	}

