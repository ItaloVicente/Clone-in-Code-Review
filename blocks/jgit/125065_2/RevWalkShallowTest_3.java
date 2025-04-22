	private RevCommit[] setupMergeChain() throws Exception {
		RevCommit[] commits = new RevCommit[6];
		commits[0] = commit();
		commits[1] = commit(commits[0]);
		commits[2] = commit(commits[1]);
		commits[3] = commit(commits[1]);
		commits[4] = commit(commits[3]);
		commits[5] = commit(commits[2]
		return commits;
	}
