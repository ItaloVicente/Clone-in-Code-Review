	@Test
	public void simpleNoForceRefsChangedEvents() throws IOException {
		writeLooseRefs("refs/heads/master"
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(B
						UPDATE_NONFASTFORWARD));
		execute(newBatchUpdate(cmds));

		assertEquals(atomic ? initialRefsChangedEvents
				: initialRefsChangedEvents + 1
	}

