			if (preserveMerges)
				return cherryPickCommitPreservingMerges(commitToPick);
			else
				return cherryPickCommitFlattening(commitToPick);
		} finally {
			monitor.endTask();
		}
	}

	private RebaseResult cherryPickCommitFlattening(RevCommit commitToPick)
			throws IOException
			UnmergedPathsException
			WrongRepositoryStateException
		newHead = tryFastForward(commitToPick);
		lastStepWasForward = newHead != null;
		if (!lastStepWasForward) {
			String ourCommitName = getOurCommitName();
			CherryPickResult cherryPickResult = new Git(repo).cherryPick()
					.include(commitToPick).setOurCommitName(ourCommitName)
					.setReflogPrefix(REFLOG_PREFIX).setStrategy(strategy)
					.call();
			switch (cherryPickResult.getStatus()) {
			case FAILED:
				if (operation == Operation.BEGIN)
					return abort(RebaseResult.failed(cherryPickResult
							.getFailingPaths()));
				else
					return stop(commitToPick
			case CONFLICTING:
				return stop(commitToPick
			case OK:
				newHead = cherryPickResult.getNewHead();
			}
		}
		return null;
	}

	private RebaseResult cherryPickCommitPreservingMerges(RevCommit commitToPick)
			throws IOException
			UnmergedPathsException
			WrongRepositoryStateException

		writeCurrentCommit(commitToPick);

		List<RevCommit> newParents = getNewParents(commitToPick);
		boolean otherParentsUnchanged = true;
		for (int i = 1; i < commitToPick.getParentCount(); i++)
			otherParentsUnchanged &= newParents.get(i).equals(
					commitToPick.getParent(i));
		newHead = otherParentsUnchanged ? tryFastForward(commitToPick) : null;
		lastStepWasForward = newHead != null;
		if (!lastStepWasForward) {
			ObjectId headId = getHead().getObjectId();
			if (!AnyObjectId.equals(headId
				checkoutCommit(headId.getName()

			if (otherParentsUnchanged) {
				boolean isMerge = commitToPick.getParentCount() > 1;
