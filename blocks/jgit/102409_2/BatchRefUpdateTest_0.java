	@Test
	public void coreLogAllRefUpdatesTrue() throws Exception {
		setLogAllRefUpdates(LogAllRefUpdates.TRUE);
		writeRef("refs/heads/master"
		writeRef("refs/tags/v1"

		Map<String
				getLastReflogs("refs/heads/master"
		assertEquals(Collections.singleton("refs/heads/master")

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogUnchanged(oldLogs
	}

	@Test
	public void coreLogAllRefUpdatesAlways() throws Exception {
		setLogAllRefUpdates(LogAllRefUpdates.ALWAYS);
		writeRef("refs/heads/master"
		writeRef("refs/tags/v1"

		Map<String
				getLastReflogs("refs/heads/master"
		assertEquals(
				new HashSet<>(Arrays.asList("refs/heads/master"
				oldLogs.keySet());

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/tags/v1"));
	}

