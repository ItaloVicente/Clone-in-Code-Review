		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(B, A, "refs/heads/masters",
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(ReceiveCommand.Result.OK, commands.get(1).getResult());
		assertEquals("[HEAD, refs/heads/master, refs/heads/masters]", refs
				.keySet().toString());
		assertEquals(B.getId(), refs.get("refs/heads/master").getObjectId());
		assertEquals(A.getId(), refs.get("refs/heads/masters").getObjectId());
