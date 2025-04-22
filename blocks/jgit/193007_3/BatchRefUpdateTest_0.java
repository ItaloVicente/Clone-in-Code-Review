	@Test
	public void refLogNotWrittenForRemoteTrackingRefs() throws Exception {
		assumeFalse(useReftable);
		setLogAllRefUpdates(true);

		String remoteTrackingRef = Constants.R_REMOTES + "heads/master";
		writeRef(remoteTrackingRef

		Map<String
		assertTrue(oldLogs.isEmpty());

		List<ReceiveCommand> cmds = Arrays
				.asList(new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogUnchanged(oldLogs
	}

