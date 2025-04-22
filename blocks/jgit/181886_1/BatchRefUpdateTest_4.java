	}

	@Test
	public void conflictThanksToDeleteRefsChangedEvents() throws IOException {
		writeLooseRefs("refs/heads/master"
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
				new ReceiveCommand(B
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertEquals(batchesRefUpdates() ? initialRefsChangedEvents + 1
				: initialRefsChangedEvents + 3
