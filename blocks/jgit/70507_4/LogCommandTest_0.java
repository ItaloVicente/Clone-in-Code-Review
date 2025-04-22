
	@Test
	public void logOnlyMergeCommits() throws Exception {
		setCommitsAndMerge();
		Git git = Git.wrap(db);

		Iterable<RevCommit> commits = git.log().all().call();
		Iterator<RevCommit> i = commits.iterator();
		RevCommit commit = i.next();
		assertEquals("merge s0 with m1"
		commit = i.next();
		assertEquals("s0"
		commit = i.next();
		assertEquals("m1"
		commit = i.next();
		assertEquals("m0"
		assertFalse(i.hasNext());

		commits = git.log().setRevFilter(RevFilter.ONLY_MERGES).call();
		i = commits.iterator();
		commit = i.next();
		assertEquals("merge s0 with m1"
		assertFalse(i.hasNext());
	}

	@Test
	public void logNoMergeCommits() throws Exception {
		setCommitsAndMerge();
		Git git = Git.wrap(db);

		Iterable<RevCommit> commits = git.log().all().call();
		Iterator<RevCommit> i = commits.iterator();
		RevCommit commit = i.next();
		assertEquals("merge s0 with m1"
		commit = i.next();
		assertEquals("s0"
		commit = i.next();
		assertEquals("m1"
		commit = i.next();
		assertEquals("m0"
		assertFalse(i.hasNext());

		commits = git.log().setRevFilter(RevFilter.NO_MERGES).call();
		i = commits.iterator();
		commit = i.next();
		assertEquals("m1"
		commit = i.next();
		assertEquals("s0"
		commit = i.next();
		assertEquals("m0"
		assertFalse(i.hasNext());
	}

	private void setCommitsAndMerge() throws Exception {
		Git git = Git.wrap(db);
		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit masterCommit0 = git.commit().setMessage("m0").call();

		createBranch(masterCommit0
		checkoutBranch("refs/heads/side");

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit c = git.commit().setMessage("s0").call();

		checkoutBranch("refs/heads/master");

		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		git.commit().setMessage("m1").call();

		git.merge().include(c.getId())
				.setStrategy(MergeStrategy.RESOLVE)
				.setMessage("merge s0 with m1").call();
	}

