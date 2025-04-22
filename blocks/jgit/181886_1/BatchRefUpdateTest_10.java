	@Test
	public void oneRefLockFailureRefsChangedEvents() throws Exception {
		assumeFalse(useReftable);

		writeLooseRef("refs/heads/master"
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(zeroId()
				new ReceiveCommand(A

		LockFile myLock = new LockFile(refdir.fileFor("refs/heads/master"));
		assertTrue(myLock.lock());
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertEquals(atomic ? initialRefsChangedEvents
					: initialRefsChangedEvents + 1
		} finally {
			myLock.unlock();
		}
	}

