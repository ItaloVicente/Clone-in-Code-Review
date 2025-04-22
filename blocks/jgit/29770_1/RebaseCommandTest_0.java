	@Test
	public void testRebasePreservingMerges1() throws Exception {
		doTestRebasePreservingMerges(true);
	}

	@Test
	public void testRebasePreservingMerges2() throws Exception {
		doTestRebasePreservingMerges(false);
	}

	private void doTestRebasePreservingMerges(boolean testConflict)
			throws Exception {
		RevWalk rw = new RevWalk(db);

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit a = git.commit().setMessage("commit a").call();

		createBranch(a

		writeTrashFile(FILE1
		writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit b = git.commit().setMessage("commit b").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		RevCommit c = git.commit().setMessage("commit c").call();

		createBranch(c

		writeTrashFile("file2"
		if (testConflict)
			writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit d = git.commit().setMessage("commit d").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/side");
		writeTrashFile("file3"
		if (testConflict)
			writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit e = git.commit().setMessage("commit e").call();

		checkoutBranch("refs/heads/topic");
		MergeResult result = git.merge().include(e.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		final RevCommit f;
		if (testConflict) {
			assertEquals(MergeStatus.CONFLICTING
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			f = git.commit().setMessage("commit f").call();
		} else {
			assertEquals(MergeStatus.MERGED
			f = rw.parseCommit(result.getNewHead());
		}

		RebaseResult res = git.rebase().setUpstream("refs/heads/master")
				.setPreserveMerges(true).call();
		if (testConflict) {
			assertEquals(Status.STOPPED
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			assertTrue(read("conflict").contains("\nb\n=======\nd\n"));
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			res = git.rebase().setOperation(Operation.CONTINUE).call();

			assertEquals(Status.STOPPED
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			assertTrue(read("conflict").contains("\nb\n=======\ne\n"));
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			res = git.rebase().setOperation(Operation.CONTINUE).call();

			assertEquals(Status.STOPPED
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			assertTrue(read("conflict").contains("\nd new\n=======\ne new\n"));
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			res = git.rebase().setOperation(Operation.CONTINUE).call();
		}
		assertEquals(Status.OK

		if (testConflict)
			assertEquals("f new resolved"
		assertEquals("blah"
		assertEquals("file2"
		assertEquals("more change"

		rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
		RevCommit newF = rw.next();
		assertDerivedFrom(newF
		assertEquals(2
		RevCommit newD = rw.next();
		assertDerivedFrom(newD
		if (testConflict)
			assertEquals("d new"
		RevCommit newE = rw.next();
		assertDerivedFrom(newE
		if (testConflict)
			assertEquals("e new"
		assertEquals(newD
		assertEquals(newE
		assertDerivedFrom(rw.next()
		assertEquals(b
		assertEquals(a
	}

	private String readFile(String path
		TreeWalk walk = TreeWalk.forPath(db
		ObjectLoader loader = db.open(walk.getObjectId(0)
		String result = RawParseUtils.decode(loader.getCachedBytes());
		walk.release();
		return result;
	}

	@Test
	public void testRebasePreservingMergesWithUnrelatedSide1() throws Exception {
		doTestRebasePreservingMergesWithUnrelatedSide(true);
	}

	@Test
	public void testRebasePreservingMergesWithUnrelatedSide2() throws Exception {
		doTestRebasePreservingMergesWithUnrelatedSide(false);
	}

	private void doTestRebasePreservingMergesWithUnrelatedSide(
			boolean testConflict) throws Exception {
		RevWalk rw = new RevWalk(db);
		rw.sort(RevSort.TOPO);

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit a = git.commit().setMessage("commit a").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit b = git.commit().setMessage("commit b").call();

		createBranch(b
		checkoutBranch("refs/heads/topic");

		writeTrashFile("file3"
		writeTrashFile(FILE1
		git.add().addFilepattern("file3").addFilepattern(FILE1).call();
		RevCommit c = git.commit().setMessage("commit c").call();

		createBranch(a
		checkoutBranch("refs/heads/side");
		writeTrashFile("conflict"
		writeTrashFile(FILE1
		git.add().addFilepattern(".").call();
		RevCommit e = git.commit().setMessage("commit e").call();

		checkoutBranch("refs/heads/topic");
		MergeResult result = git.merge().include(e)
				.setStrategy(MergeStrategy.RESOLVE).call();

		assertEquals(MergeStatus.CONFLICTING
		assertEquals(result.getConflicts().keySet()
				Collections.singleton(FILE1));
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit d = git.commit().setMessage("commit d").call();

		RevCommit f = commitFile("file2"

		checkoutBranch("refs/heads/master");
		writeTrashFile("fileg"
		if (testConflict)
			writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit g = git.commit().setMessage("commit g").call();

		checkoutBranch("refs/heads/topic");
		RebaseResult res = git.rebase().setUpstream("refs/heads/master")
				.setPreserveMerges(true).call();
		if (testConflict) {
			assertEquals(Status.STOPPED
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			res = git.rebase().setOperation(Operation.CONTINUE).call();
		}
		assertEquals(Status.OK

		assertEquals("merge resolution"
		assertEquals("new content two"
		assertEquals("more changess"
		assertEquals("fileg"

		rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
		RevCommit newF = rw.next();
		assertDerivedFrom(newF
		RevCommit newD = rw.next();
		assertDerivedFrom(newD
		assertEquals(2
		RevCommit newC = rw.next();
		assertDerivedFrom(newC
		RevCommit newE = rw.next();
		assertEquals(e
		assertEquals(newC
		assertEquals(e
		assertEquals(g
		assertEquals(b
		assertEquals(a
	}

