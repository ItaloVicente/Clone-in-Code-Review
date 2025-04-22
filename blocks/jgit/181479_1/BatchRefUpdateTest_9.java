	@Test
	public void packedRefsLockFailureRefsChangedEvents() throws Exception {
		writeLooseRef("refs/heads/master"
		refdir.exactRef("refs/heads/master");
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()

		LockFile myLock = refdir.lockPackedRefs();
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertEquals(
					batchesRefUpdates() ? initialRefsChangedEvents
							: initialRefsChangedEvents + 2
					refsChangedEvents);
		} finally {
			myLock.unlock();
		}
	}

