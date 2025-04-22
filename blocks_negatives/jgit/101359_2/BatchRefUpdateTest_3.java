		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(B, A, "refs/heads/master",
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo) {
			@Override
			public boolean isMergedInto(RevCommit base, RevCommit tip) {
				throw new AssertionError("isMergedInto() should not be called");
			}
		}, new StrictWorkMonitor());
		Map<String, Ref> refs = refdir.getRefs(RefDatabase.ALL);
		assertEquals(ReceiveCommand.Result.OK, commands.get(0).getResult());
		assertEquals(A.getId(), refs.get("refs/heads/master").getObjectId());
