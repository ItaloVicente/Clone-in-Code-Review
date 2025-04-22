
			repoNames.add(DecoratableResourceHelper
					.getRepositoryName(repository));

			if (discoveredBranch == null) {
				discoveredBranch = DecoratableResourceHelper
						.getShortBranch(repository);
				discoveredBranchStatus = DecoratableResourceHelper
						.getBranchStatus(repository);
			}
		}

		if(repoNames.size() == 1) {
			repositoryName = repoNames.iterator().next();
			branch = discoveredBranch;
			branchStatus = discoveredBranchStatus;
