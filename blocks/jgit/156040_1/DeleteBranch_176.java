	public void execute() throws IOException {
		try {
			git._branchDelete().setBranchNames(branch.getName()).setForce(true).call();
		} catch (final GitAPIException e) {
			throw new IOException(e);
		}
	}
