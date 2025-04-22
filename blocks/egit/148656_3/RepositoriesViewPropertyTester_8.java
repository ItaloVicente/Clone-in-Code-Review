		if (ref.getName().startsWith(Constants.R_REFS)) {
			return ref.getName().equals(
					RepositoryStateCache.INSTANCE.getFullBranchName(repository));
		} else if (ref.getName().equals(Constants.HEAD)) {
			return true;
		} else {
			String leafname = ref.getLeaf().getName();
			if (leafname.startsWith(Constants.R_REFS) && leafname.equals(
					RepositoryStateCache.INSTANCE.getFullBranchName(repository))) {
