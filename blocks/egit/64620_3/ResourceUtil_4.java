		if (provider != null) {
			if (provider instanceof RepositoryProvider) {
				markAsShared(project, ((RepositoryProvider) provider).getID());
				if (provider instanceof GitProvider) {
					return (GitProvider) provider;
				}
			} else {
				markAsUnshared(project);
			}
