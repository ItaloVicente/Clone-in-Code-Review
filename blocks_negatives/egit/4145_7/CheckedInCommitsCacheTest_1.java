	private void assertEmptyCommit(Commit commit, RevCommit actualCommit, int direction) {
		commonCommitAsserts(commit, actualCommit);
		assertThat(commit.getChildren(), nullValue());
		assertThat(commit.getDirection(), is(direction));
	}

