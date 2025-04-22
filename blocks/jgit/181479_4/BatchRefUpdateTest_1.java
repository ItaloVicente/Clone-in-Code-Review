	}

	@Test
	public void simpleForceRefsChangedEvents() throws IOException {
		writeLooseRefs("refs/heads/master"
		if (!useReftable) {
			refdir.exactRef("refs/heads/master");
			refdir.exactRef("refs/heads/masters");
		}
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(B
						UPDATE_NONFASTFORWARD));
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertEquals(batchesRefUpdates() ? initialRefsChangedEvents + 1
				: initialRefsChangedEvents + 2
