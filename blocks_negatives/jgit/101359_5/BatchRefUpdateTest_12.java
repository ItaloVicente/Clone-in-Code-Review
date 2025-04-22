		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(A, zeroId(), "refs/heads/foo2",
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(1).getResult());
