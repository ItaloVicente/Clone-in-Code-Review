	@Test
	public void updateCreateDelete() throws IOException {
		writeRef("refs/heads/master"
		writeRef("refs/heads/branch2"
		writeRef("refs/heads/branch3"
		int initialRefsChangedEvents = refsChangedEvents;

		Map<String
				"refs/heads/branch1"
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true)
				.setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs("refs/heads/master"
				"refs/heads/branch2"
		assertEquals(batchesRefUpdates() ? initialRefsChangedEvents + 1
				: initialRefsChangedEvents + 3
		assertReflogEquals(reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch1"));
		assertReflogUnchanged(oldLogs
	}

