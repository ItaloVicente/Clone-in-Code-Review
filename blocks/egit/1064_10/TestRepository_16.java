	public void checkoutBranch(String refName) throws CoreException {
		new BranchOperation(repository, refName).execute(null);
	}

	public void addToIndex(IProject project, File file) throws Exception {
		IFile iFile = getIFile(project, file);
		addToIndex(iFile);
	}


