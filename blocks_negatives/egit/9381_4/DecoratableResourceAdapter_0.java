				break;
			case IResource.PROJECT:
				repositoryName = DecoratableResourceHelper
						.getRepositoryName(repository);
				branch = DecoratableResourceHelper.getShortBranch(repository);
				branchStatus = DecoratableResourceHelper.getBranchStatus(repository);
				tracked = true;
			case IResource.FOLDER:
				extractContainerProperties();
				break;
