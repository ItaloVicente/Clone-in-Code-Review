		String refName = ref.getLeaf().getName();
		final BranchOperationUI op;
		if (refName.startsWith(Constants.R_REFS))
			op = new BranchOperationUI(repo, ref.getName());
		else
			op = new BranchOperationUI(repo, ref.getLeaf().getObjectId());

