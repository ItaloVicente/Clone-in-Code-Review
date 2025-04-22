
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
				new ReceiveCommand(B
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/masters/x"
