			@Override
			public boolean isMergedInto(RevCommit base
				throw new AssertionError("isMergedInto() should not be called");
			}
		}) {
			newBatchUpdate(cmds).setAllowNonFastForwards(true).execute(rw
					new StrictWorkMonitor());
