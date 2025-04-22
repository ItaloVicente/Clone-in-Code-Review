			RepositoryState state = repo.getRepositoryState();
			final boolean merging = state.equals(RepositoryState.MERGING)
					|| state.equals(RepositoryState.MERGING_RESOLVED);
			final boolean cherryPicking = state
					.equals(RepositoryState.CHERRY_PICKING)
					|| state.equals(RepositoryState.CHERRY_PICKING_RESOLVED);
