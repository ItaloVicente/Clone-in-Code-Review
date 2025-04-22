		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A, B, "refs/heads/master",
						ReceiveCommand.Type.UPDATE),
				new ReceiveCommand(zeroId(), A, "refs/heads/master/x",
						ReceiveCommand.Type.CREATE),
				new ReceiveCommand(zeroId(), A, "refs/heads",
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo), NullProgressMonitor.INSTANCE);
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
