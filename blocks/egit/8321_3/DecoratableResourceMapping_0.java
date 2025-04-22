
		if(repositories.size() == 1) {
			Repository repository = repositories.iterator().next();
			repositoryName = DecoratableResourceHelper
					.getRepositoryName(repository);
			branch = DecoratableResourceHelper.getShortBranch(repository);
			branchStatus = DecoratableResourceHelper
					.getBranchStatus(repository);
		} else if(repositories.size() > 1) {
			repositoryName = MULTIPLE;
			Set<String> branches = new HashSet<String>(repositories.size());
			for (Repository repository : repositories) {
				branches.add(DecoratableResourceHelper
						.getShortBranch(repository));
			}
			if (branches.size() == 1) {
				branch = branches.iterator().next();
			} else {
				branch = MULTIPLE;
			}
		}

