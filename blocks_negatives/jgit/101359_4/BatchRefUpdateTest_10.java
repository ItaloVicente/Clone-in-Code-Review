		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/foo1",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), B, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE,
				commands.get(0).getResult());
