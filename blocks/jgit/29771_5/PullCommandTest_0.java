	private enum TestPullMode {
		MERGE
	}

	@Test
	public void testPullWithRebasePreserve1Config() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebasePreserveConfig2() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebasePreserveConfig3() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebaseConfig1() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebaseConfig2() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebaseConfig3() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithoutConfig() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithMergeConfig() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithMergeConfig2() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	private void doTestPullWithRebase(Callable<PullResult> pullSetup
			TestPullMode expectedPullMode) throws Exception {
		writeToFile(sourceFile
		source.add().addFilepattern(sourceFile.getName()).call();
		RevCommit sourceCommit = source.commit().setMessage("source commit")
				.call();

		File loxalFile = new File(dbTarget.getWorkTree()
		writeToFile(loxalFile
		target.add().addFilepattern("local.txt").call();
		RevCommit t1 = target.commit().setMessage("target commit 1").call();

		target.checkout().setCreateBranch(true).setName("side").call();

		String newContent = "initial\n" + "and more\n";
		writeToFile(loxalFile
		target.add().addFilepattern("local.txt").call();
		RevCommit t2 = target.commit().setMessage("target commit 2").call();

		target.checkout().setName("master").call();

		MergeResult mergeResult = target.merge()
				.setFastForward(MergeCommand.FastForwardMode.NO_FF).include(t2)
				.call();
		assertEquals(MergeStatus.MERGED
		assertFileContentsEqual(loxalFile
		ObjectId merge = mergeResult.getNewHead();

		PullResult res = pullSetup.call();
		assertNotNull(res.getFetchResult());

		if (expectedPullMode == TestPullMode.MERGE) {
			assertEquals(MergeStatus.MERGED
					.getMergeStatus());
			assertNull(res.getRebaseResult());
		} else {
			assertNull(res.getMergeResult());
			assertEquals(RebaseResult.OK_RESULT
		}
		assertFileContentsEqual(sourceFile

		RevWalk rw = new RevWalk(dbTarget);
		rw.sort(RevSort.TOPO);
		rw.markStart(rw.parseCommit(dbTarget.resolve("refs/heads/master")));

		RevCommit next;
		if (expectedPullMode == TestPullMode.MERGE) {
			next = rw.next();
			assertEquals(2
			assertEquals(merge
			assertEquals(sourceCommit
		} else {
			if (expectedPullMode == TestPullMode.REBASE_PREASERVE) {
				next = rw.next();
				assertEquals(2
			}
			next = rw.next();
			assertEquals(t2.getShortMessage()
			next = rw.next();
			assertEquals(t1.getShortMessage()
			next = rw.next();
			assertEquals(sourceCommit
			next = rw.next();
			assertEquals("Initial commit for source"
			next = rw.next();
			assertNull(next);
		}
		rw.release();
	}

