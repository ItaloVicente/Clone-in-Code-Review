	@Test
	public void overrideRefLogMessage() throws Exception {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		cmds.get(0).setRefLogMessage("custom log"
		PersonIdent ident = new PersonIdent(diskRepo);
		execute(
				newBatchUpdate(cmds)
						.setRefLogIdent(ident)
						.setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master")
				true);
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch")
				true);
	}

	@Test
	public void overrideDisableRefLog() throws Exception {
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		cmds.get(0).disableRefLog();
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogUnchanged(oldLogs
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch"));
	}

