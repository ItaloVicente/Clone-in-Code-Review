	@Test
	public void updateToMissingObjectRefsChangedEvents() throws IOException {
		writeLooseRef("refs/heads/master"
		refdir.exactRef("refs/heads/master");
		int initialRefsChangedEvents = refsChangedEvents;

		ObjectId bad = ObjectId
				.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true)

		assertEquals(atomic ? initialRefsChangedEvents
				: initialRefsChangedEvents + 1
	}

