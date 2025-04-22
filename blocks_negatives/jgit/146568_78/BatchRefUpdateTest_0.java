					@Override
					public boolean isMergedInto(RevCommit base, RevCommit tip) {
						throw new AssertionError("isMergedInto() should not be called");
					}
				}) {
			newBatchUpdate(cmds)
					.setAllowNonFastForwards(true)
					.execute(rw, new StrictWorkMonitor());
