	@Test
	public void packedRefsLockFailureRefsChangedEvents() throws Exception {
		assumeFalse(useReftable);

		writeLooseRef("refs/heads/master"
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()

		LockFile myLock = refdir.lockPackedRefs();
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertEquals(atomic ? initialRefsChangedEvents
					: initialRefsChangedEvents + 2
		} finally {
			myLock.unlock();
		}
	}

