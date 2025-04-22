		subscriber.reset(this.gsds);
		ResourceTraversal[] traversals = getScopeManager().getScope()
				.getTraversals();
		try {
			subscriber.refresh(traversals, new NullProgressMonitor());
		} catch (TeamException e) {
			logRefreshException(e);
