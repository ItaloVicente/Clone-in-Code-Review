						.setReflogPrefix(REFLOG_PREFIX).setStrategy(strategy);
				if (isMerge) {
					pickCommand.setMainlineParentNumber(1);
					pickCommand.setNoCommit(true);
					writeMergeInfo(commitToPick
				}
				CherryPickResult cherryPickResult = pickCommand.call();
