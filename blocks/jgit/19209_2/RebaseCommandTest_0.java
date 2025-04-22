	@Test
	public void testRebaseWithAutoStash()
			throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"

		assertEquals(Status.OK
				git.rebase().setUpstream("refs/heads/master").call()
						.getStatus());
		checkFile(new File(db.getWorkTree()
				"unstaged modified file0");
		checkFile(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals("[file0
				+ "[file1
				+ "[file2
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRebaseWithAutoStashConflictOnApply() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file1"

		assertEquals(Status.STASH_APPLY_CONFLICTS
				git.rebase().setUpstream("refs/heads/master").call()
						.getStatus());
		checkFile(new File(db.getWorkTree()
		checkFile(
				new File(db.getWorkTree()
				"<<<<<<< HEAD\nmodified file1\n=======\nunstaged modified file1\n>>>>>>> stash\n");
		checkFile(new File(db.getWorkTree()
		assertEquals(
				"[file0
						+ "[file1
						+ "[file1
						+ "[file1
						+ "[file2
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE

		List<DiffEntry> diffs = getStashedDiff();
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
		assertEquals("file1"
	}

	private List<DiffEntry> getStashedDiff() throws AmbiguousObjectException
			IncorrectObjectTypeException
		ObjectId stashId = db.resolve("stash@{0}");
		RevWalk revWalk = new RevWalk(db);
		RevCommit stashCommit = revWalk.parseCommit(stashId);
		List<DiffEntry> diffs = diffWorkingAgainstHead(stashCommit
		return diffs;
	}

	private TreeWalk createTreeWalk() {
		TreeWalk walk = new TreeWalk(db);
		walk.setRecursive(true);
		walk.setFilter(TreeFilter.ANY_DIFF);
		return walk;
	}

	private List<DiffEntry> diffWorkingAgainstHead(final RevCommit commit
			RevWalk revWalk)
			throws IOException {
		TreeWalk walk = createTreeWalk();
		RevCommit parentCommit = revWalk.parseCommit(commit.getParent(0));
		try {
			walk.addTree(parentCommit.getTree());
			walk.addTree(commit.getTree());
			return DiffEntry.scan(walk);
		} finally {
			walk.release();
		}
	}

