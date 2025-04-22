		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, bad, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), B, "refs/heads/foo2",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo), NullProgressMonitor.INSTANCE);
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT,
				commands.get(0).getResult());
