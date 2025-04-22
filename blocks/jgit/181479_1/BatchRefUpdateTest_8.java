		}

	@Test
	public void nonExistentRefRefsChangedEvents() throws IOException {
		writeLooseRef("refs/heads/master"
		refdir.exactRef("refs/heads/master");
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertEquals(batchesRefUpdates() ? initialRefsChangedEvents
				: initialRefsChangedEvents + 1
