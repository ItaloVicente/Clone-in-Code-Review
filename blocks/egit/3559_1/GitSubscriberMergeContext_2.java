	@Override
	public void dispose() {
		GitProjectData.removeRepositoryChangeListener(repoChangeListener);
		super.dispose();
	}


	private void update(GitResourceVariantTreeSubscriber subscriber,
			RepositoryMapping which) {
		for (GitSynchronizeData gsd : gsds) {
			if (which.getRepository().equals(gsd.getRepository())) {
				try {
					gsd.updateRevs();
				} catch (IOException e) {
					Activator.error("Failed refresh Rev's", e); //$NON-NLS-1$

					return;
				}

				subscriber.reset(this.gsds);

				ResourceTraversal[] traversals = getScopeManager().getScope()
						.getTraversals();
				try {
					subscriber.refresh(traversals, new NullProgressMonitor());
				} catch (CoreException e) {
					Activator.error("Failed refresh synchronize view", e); //$NON-NLS-1$
				}

				return;
			}
		}
	}

