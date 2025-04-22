	@Test
	public void oneRefWrongOldValueRefsChangedEvents() throws IOException {
		writeLooseRef("refs/heads/master"
		refdir.exactRef("refs/heads/master");
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(B
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertEquals(batchesRefUpdates() ? initialRefsChangedEvents
				: initialRefsChangedEvents + 1
	}

