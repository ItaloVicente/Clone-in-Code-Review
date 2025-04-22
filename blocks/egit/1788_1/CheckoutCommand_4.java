		Repository repo = node.getRepository();
		String refName = ref.getLeaf().getName();
		final BranchOperation op;
		if (refName.startsWith(Constants.R_REFS))
			op = new BranchOperation(repo, ref.getName());
		else
			op = new BranchOperation(repo, ref.getLeaf().getObjectId());
