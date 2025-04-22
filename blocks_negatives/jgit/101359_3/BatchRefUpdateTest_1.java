		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(B, A, "refs/heads/masters",
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.REJECTED_NONFASTFORWARD, commands
				.get(1).getResult());
