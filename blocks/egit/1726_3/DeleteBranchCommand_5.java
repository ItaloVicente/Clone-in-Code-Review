	private int deleteBranch(final RefNode node, final Ref ref)
			throws CoreException {
		DeleteBranchOperation dbop = new DeleteBranchOperation(node
				.getRepository(), ref, true);
		dbop.execute(null);
		return dbop.getStatus();
