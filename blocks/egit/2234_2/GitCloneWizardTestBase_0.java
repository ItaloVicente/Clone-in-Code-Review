	protected void cloneRepo(File destinationRepo,
			RepoRemoteBranchesPage remoteBranches) throws Exception {
		remoteBranches.assertRemoteBranches(SampleTestRepository.FIX,
				Constants.MASTER);
		remoteBranches.selectBranches(SampleTestRepository.FIX,
				Constants.MASTER);
