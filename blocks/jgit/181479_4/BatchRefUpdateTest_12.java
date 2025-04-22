	@Test
	public void singleRefUpdateDoesNotRequirePackedRefsLockRefsChangedEvents()
			throws Exception {
		assumeFalse(useReftable);
		writeLooseRef("refs/heads/master"
		refdir.exactRef("refs/heads/master");
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays
				.asList(new ReceiveCommand(A

		LockFile myLock = refdir.lockPackedRefs();
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertEquals(initialRefsChangedEvents + 1
		} finally {
			myLock.unlock();
		}
	}

