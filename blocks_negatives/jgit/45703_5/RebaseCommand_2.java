			if (otherParentsUnchanged) {
				boolean isMerge = commitToPick.getParentCount() > 1;
				String ourCommitName = getOurCommitName();
				CherryPickCommand pickCommand = new Git(repo).cherryPick()
						.include(commitToPick).setOurCommitName(ourCommitName)
						.setReflogPrefix(REFLOG_PREFIX).setStrategy(strategy);
				if (isMerge) {
					pickCommand.setMainlineParentNumber(1);
					pickCommand.setNoCommit(true);
					writeMergeInfo(commitToPick, newParents);
				}
				CherryPickResult cherryPickResult = pickCommand.call();
				switch (cherryPickResult.getStatus()) {
				case FAILED:
					if (operation == Operation.BEGIN)
						return abort(RebaseResult.failed(cherryPickResult
								.getFailingPaths()));
					else
						return stop(commitToPick, Status.STOPPED);
				case CONFLICTING:
					return stop(commitToPick, Status.STOPPED);
				case OK:
