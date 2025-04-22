	private RevCommit[] setupLinearChain() throws Exception {
		RevCommit[] commits = new RevCommit[4];
		RevCommit parent = null;
		for (int i = 0; i < commits.length; i++) {
			commits[i] = parent != null ? commit(parent) : commit();
			parent = commits[i];
		}
		return commits;
	}

