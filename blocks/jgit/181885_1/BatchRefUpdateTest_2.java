	}

	@Test
	public void nonFastForwardDoesNotDoExpensiveMergeCheckRefsChangedEvents()
			throws IOException {
		writeLooseRef("refs/heads/master"
		int initialRefsChangedEvents = refsChangedEvents;

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

		assertEquals(initialRefsChangedEvents + 1
