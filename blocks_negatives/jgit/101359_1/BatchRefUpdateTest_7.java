		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), A, "refs/heads/masters/x",
						ReceiveCommand.Type.CREATE),
				new ReceiveCommand(B, zeroId(), "refs/heads/masters",
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(1).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(2).getResult());
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters/x]", refs
				.keySet().toString());
		assertEquals(A.getId(), refs.get("refs/heads/masters/x").getObjectId());
