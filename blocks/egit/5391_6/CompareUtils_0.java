	public static void compare(IResource[] resources, Repository repository,
			String leftPath, String rightPath, String leftRev, String rightRev,
			boolean includeLocal, IWorkbenchPage page) throws IOException {
		if (resources.length == 1 && resources[0] instanceof IFile
				&& canDirectlyOpenInCompare((IFile) resources[0])) {
			if (includeLocal)
				compareWorkspaceWithRef(repository, (IFile) resources[0],
						rightRev, page);
			else {
				compareBetween(repository, leftPath, rightPath, leftRev,
						rightRev, page);
			}
		} else
			GitModelSynchronize.synchronize(resources, repository, leftRev,
					rightRev, includeLocal);
	}

