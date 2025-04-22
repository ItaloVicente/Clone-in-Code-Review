
		List<Commit> leftResult = GitCommitsModelCache.build(db, initialTagId(), c);
		List<Commit> rightResult = GitCommitsModelCache.build(db, c, initialTagId());

		assertThat(leftResult, notNullValue());
		assertThat(leftResult.size(), is(1));
		assertEmptyCommit(leftResult.get(0), c, LEFT);

		assertThat(rightResult, notNullValue());
		assertThat(rightResult.size(), is(1));
		assertEmptyCommit(rightResult.get(0), c, RIGHT);
	}

	@Test
	public void shouldListTwoEmptyCommits() throws Exception {
		Git git = new Git(db);
		RevCommit c1 = commit(git, "second commit");
		RevCommit c2 = commit(git, "third commit");

