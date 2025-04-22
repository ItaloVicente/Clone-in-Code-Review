	@Test
	public void fileDirectoryConflictRefsChangedEvents() throws IOException {
		writeLooseRefs("refs/heads/master"
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true)

		assertEquals(atomic ? initialRefsChangedEvents
				: initialRefsChangedEvents + 1
	}

