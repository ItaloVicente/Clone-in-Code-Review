	private class SubmoduleWalkJob extends Job {
		private Set<StagingEntry> nodes;

		private Repository repo;

		SubmoduleWalkJob(String name, Repository repository,
				Set<StagingEntry> nodes) {
			super(name);
			this.nodes = nodes;
			this.repo = repository;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IStatus status = Status.OK_STATUS;
			try {
				SubmoduleWalk walk = SubmoduleWalk.forIndex(repo);
				while (walk.next()) {
					for (StagingEntry entry : nodes)
						entry.setSubmodule(entry.getPath().equals(
								walk.getPath()));
				}
			} catch (IOException e) {
				status = Activator.createErrorStatus(
						UIText.StagingViewContentProvider_SubmoduleError, e);
			}
			return status;
		}
	}

