	@Test
	public void refLogNotWrittenWithoutConfigOption() throws Exception {
		setLogAllRefUpdates(false);
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"
		assertTrue(oldLogs.isEmpty());

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogUnchanged(oldLogs
		assertReflogUnchanged(oldLogs
	}

	@Test
	public void forceRefLogInUpdate() throws Exception {
		setLogAllRefUpdates(false);
		writeRef("refs/heads/master"
		assertTrue(
				getLastReflogs("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(
				newBatchUpdate(cmds)
						.setRefLogMessage("a reflog"
						.setForceRefLog(true));

		assertResults(cmds
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch"));
	}

	@Test
	public void forceRefLogInCommand() throws Exception {
		setLogAllRefUpdates(false);
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"
		assertTrue(oldLogs.isEmpty());

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		cmds.get(1).setForceRefLog(true);
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogUnchanged(oldLogs
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch"));
	}

