	@Test
	public void noRefLog() throws IOException {
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"
		assertEquals(Collections.singleton("refs/heads/master")

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch"
		assertReflogUnchanged(oldLogs
		assertReflogUnchanged(oldLogs
	}

	@Test
	public void reflogDefaultIdent() throws IOException {
		writeRef("refs/heads/master"
		writeRef("refs/heads/branch2"

		Map<String
				"refs/heads/master"
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(
				newBatchUpdate(cmds)
						.setAllowNonFastForwards(true)
						.setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch1"
				"refs/heads/branch2"
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch1"));
		assertReflogUnchanged(oldLogs
	}

	@Test
	public void reflogAppendStatusNoMessage() throws IOException {
		writeRef("refs/heads/master"
		writeRef("refs/heads/branch1"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(B
				new ReceiveCommand(zeroId()
		execute(
				newBatchUpdate(cmds)
						.setAllowNonFastForwards(true)
						.setRefLogMessage(null

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch1"
				"refs/heads/branch2"
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(B
				getLastReflog("refs/heads/branch1"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch2"));
	}

	@Test
	public void reflogAppendStatusFastForward() throws IOException {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setRefLogMessage(null

		assertResults(cmds
		assertRefs("refs/heads/master"
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
	}

	@Test
	public void reflogAppendStatusWithMessage() throws IOException {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch"
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch"));
	}

	@Test
	public void reflogCustomIdent() throws IOException {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		PersonIdent ident = new PersonIdent("A Reflog User"
		execute(
				newBatchUpdate(cmds)
						.setRefLogMessage("a reflog"
						.setRefLogIdent(ident));

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch"
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
	public void reflogDelete() throws IOException {
		writeRef("refs/heads/master"
		writeRef("refs/heads/branch"
		assertEquals(
				2

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs("refs/heads/branch"
		assertNull(getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/branch"));
	}

	@Test
	public void reflogFileDirectoryConflict() throws IOException {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs("refs/heads/master/x"
		assertNull(getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/master/x"));
	}

