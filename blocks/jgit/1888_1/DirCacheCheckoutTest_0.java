
	public void testResetHard() throws IOException
			GitAPIException {
		Git git = new Git(db);
		writeTrashFile("f"
		writeTrashFile("D/g"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("inital").call();
		assertIndex(mkmap("f"

		git.branchCreate().setName("topic").call();

		writeTrashFile("f"
		writeTrashFile("D/g"
		writeTrashFile("E/h"
		git.add().addFilepattern(".").call();
		RevCommit master = git.commit().setMessage("master-1").call();
		assertIndex(mkmap("f"

		checkoutBranch("refs/heads/topic");
		assertIndex(mkmap("f"

		writeTrashFile("f"
		assertTrue(new File(db.getWorkTree()
		writeTrashFile("G/i"
		git.add().addFilepattern(".").call();
		git.add().addFilepattern(".").setUpdate(true).call();
		RevCommit topic = git.commit().setMessage("topic-1").call();
		assertIndex(mkmap("f"

		resetHard(master);
		assertIndex(mkmap("f"
		resetHard(topic);
		assertIndex(mkmap("f"
		assertWorkDir(mkmap("f"

		assertEquals(MergeStatus.CONFLICTING
				.call().getMergeStatus());
		assertEquals(
				"[E/h
				indexState(0));

		resetHard(master);
		assertIndex(mkmap("f"
		assertWorkDir(mkmap("f"
				"h()"));
	}

	private DirCacheCheckout resetHard(RevCommit commit)
			throws NoWorkTreeException
			CorruptObjectException
		DirCacheCheckout dc;
		dc = new DirCacheCheckout(db
				commit.getTree());
		dc.setFailOnConflict(true);
		assertTrue(dc.checkout());
		return dc;
	}

	private void checkoutBranch(String branchName)
			throws IllegalStateException
		RevWalk walk = new RevWalk(db);
		RevCommit head = walk.parseCommit(db.resolve(Constants.HEAD));
		RevCommit branch = walk.parseCommit(db.resolve(branchName));
		DirCacheCheckout dco = new DirCacheCheckout(db
				db.lockDirCache()
		dco.setFailOnConflict(true);
		assertTrue(dco.checkout());
		walk.release();
		RefUpdate refUpdate = db.updateRef(Constants.HEAD);
		assertEquals(Result.FORCED
	}

