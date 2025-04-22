			if (resource.getType() == IResource.PROJECT) {
				repositoryName = DecoratableResourceHelper
						.getRepositoryName(repository);
				branch = DecoratableResourceHelper.getShortBranch(repository);
				branchStatus = DecoratableResourceHelper.getBranchStatus(repository);
			}
