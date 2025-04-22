	@Test
	public void fileDirectoryConflictRefsChangedEvents() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		refdir.exactRef("refs/heads/master");
		refdir.exactRef("refs/heads/masters");
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true)

		assertEquals(batchesRefUpdates() ? initialRefsChangedEvents
				: initialRefsChangedEvents + 1
	}

