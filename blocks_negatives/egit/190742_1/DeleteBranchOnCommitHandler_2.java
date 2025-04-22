	private int deleteBranch(Repository repo, final Ref ref, boolean force)
			throws CoreException {
		DeleteBranchOperation dbop = new DeleteBranchOperation(repo, ref, force);
		dbop.execute(null);
		return dbop.getStatus();
	}

