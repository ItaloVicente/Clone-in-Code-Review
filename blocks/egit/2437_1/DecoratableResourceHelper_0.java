		final WorkingTreeIterator workingTreeIterator = treeWalk.getTree(
				T_WORKSPACE, WorkingTreeIterator.class);
		if (workingTreeIterator == null)
			return null;
		if (!(workingTreeIterator instanceof ContainerTreeIterator))
			return null;
		final ContainerTreeIterator workspaceIterator = (ContainerTreeIterator) workingTreeIterator;
		final ResourceEntry resourceEntry = workspaceIterator
				.getResourceEntry();
