	@Test
	public void nonExistentRefRefsChangedEvents() throws IOException {
		writeLooseRef("refs/heads/master"
		if (!useReftable) {
			refdir.exactRef("refs/heads/master");
		}
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertEquals(atomic ? initialRefsChangedEvents
				: initialRefsChangedEvents + 1
	}

