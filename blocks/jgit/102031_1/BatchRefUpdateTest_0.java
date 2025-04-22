	@Test
	public void packedRefsLockFailure() throws Exception {
		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()

		refdir.setRetrySleepMs(Arrays.asList(0
		LockFile myLock = refdir.lockPackedRefs();
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertFalse(getLockFile("refs/heads/master").exists());
			assertFalse(getLockFile("refs/heads/branch").exists());

			if (atomic) {
				assertResults(cmds
				assertRefs("refs/heads/master"
			} else {
				assertResults(cmds
				assertRefs(
						"refs/heads/master"
						"refs/heads/branch"
			}
		} finally {
			myLock.unlock();
		}
	}

	@Test
	public void oneRefLockFailure() throws Exception {
		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(zeroId()
				new ReceiveCommand(A

		refdir.setRetrySleepMs(Arrays.asList(0
		LockFile myLock = new LockFile(refdir.fileFor("refs/heads/master"));
		assertTrue(myLock.lock());
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertFalse(LockFile.getLockFile(refdir.packedRefsFile).exists());
			assertFalse(getLockFile("refs/heads/branch").exists());

			if (atomic) {
				assertResults(cmds
				assertRefs("refs/heads/master"
			} else {
				assertResults(cmds
				assertRefs(
						"refs/heads/branch"
						"refs/heads/master"
			}
		} finally {
			myLock.unlock();
		}
	}

