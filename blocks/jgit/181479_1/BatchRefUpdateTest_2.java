		List<ReceiveCommand> cmds = Arrays.asList(new ReceiveCommand(B
				"refs/heads/master"
		try (RevWalk rw = new RevWalk(diskRepo) {
					@Override
					public boolean isMergedInto(RevCommit base
						throw new AssertionError("isMergedInto() should not be called");
					}
				}) {
			newBatchUpdate(cmds).setAllowNonFastForwards(true).execute(rw
					new StrictWorkMonitor());
		}

		assertResults(cmds
		assertRefs("refs/heads/master"
	}

	@Test
	public void nonFastForwardDoesNotDoExpensiveMergeCheckRefsChangedEvents()
			throws IOException {
		writeLooseRef("refs/heads/master"
		refdir.exactRef("refs/heads/master");
		int initialRefsChangedEvents = refsChangedEvents;

