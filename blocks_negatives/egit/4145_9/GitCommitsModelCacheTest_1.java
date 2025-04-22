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

		List<Commit> leftResult = GitCommitsModelCache.build(db, initialTagId(), c2);
		List<Commit> rightResult = GitCommitsModelCache.build(db, c2, initialTagId());

		assertThat(leftResult, notNullValue());
		assertThat(leftResult.size(), is(2));
		assertEmptyCommit(leftResult.get(0), c2, LEFT);
		assertEmptyCommit(leftResult.get(1), c1, LEFT);

		assertThat(rightResult, notNullValue());
		assertThat(rightResult.size(), is(2));
		assertEmptyCommit(rightResult.get(0), c2, RIGHT);
		assertEmptyCommit(rightResult.get(1), c1, RIGHT);
