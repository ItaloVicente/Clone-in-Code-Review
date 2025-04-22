
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(B
		try (RevWalk rw = new RevWalk(diskRepo) {
					@Override
					public boolean isMergedInto(RevCommit base
						throw new AssertionError("isMergedInto() should not be called");
					}
				}) {
			newBatchUpdate(cmds)
					.setAllowNonFastForwards(true)
					.execute(rw
		}

		assertResults(cmds
		assertRefs("refs/heads/master"
