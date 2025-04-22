	@Test
	public void simpleNoForceRefsChangedEvents() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		refdir.exactRef("refs/heads/master");
		refdir.exactRef("refs/heads/masters");
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(B
						UPDATE_NONFASTFORWARD));
		execute(newBatchUpdate(cmds));

		assertEquals(batchesRefUpdates() ? initialRefsChangedEvents
				: initialRefsChangedEvents + 1
	}

