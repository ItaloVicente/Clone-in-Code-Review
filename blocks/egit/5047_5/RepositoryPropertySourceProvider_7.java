		else if (node.getType() == RepositoryTreeNodeType.REF) {
			lastObject = object;
			Ref ref = (Ref) node.getObject();
			if (ref.getName().startsWith(Constants.R_HEADS) || ref.getName().startsWith(Constants.R_REMOTES)){
				checkChangeType(SourceType.BRANCH);
				Repository repository = (Repository) node.getAdapter(Repository.class);
				lastRepositorySource =  new BranchPropertySource(repository, ref.getName(), myPage);
				return lastRepositorySource;
			}
			return null;
		} else
