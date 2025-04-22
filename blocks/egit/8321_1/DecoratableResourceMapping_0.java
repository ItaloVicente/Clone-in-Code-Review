
			repoNames.add(DecoratableResourceHelper
					.getRepositoryName(repository));
			lastBranch = DecoratableResourceHelper.getShortBranch(repository);
			lastBranchStatus = DecoratableResourceHelper
					.getBranchStatus(repository);
		}

		if(repoNames.size() == 1) {
			repositoryName = repoNames.iterator().next();
			branch = lastBranch;
			branchStatus = lastBranchStatus;
