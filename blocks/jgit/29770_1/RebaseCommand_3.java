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
				String ourCommitName = getOurCommitName();
				CherryPickCommand pickCommand = new Git(repo).cherryPick()
						.include(commitToPick).setOurCommitName(ourCommitName)
						.setReflogPrefix(REFLOG_PREFIX).setStrategy(strategy);
				if (isMerge) {
					pickCommand.setMainlineParentNumber(1);
					pickCommand.setNoCommit(true);
					writeMergeInfo(commitToPick
				}
				CherryPickResult cherryPickResult = pickCommand.call();
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
					if (isMerge) {
						CommitCommand commit = new Git(repo).commit();
						commit.setAuthor(commitToPick.getAuthorIdent());
								+ commitToPick.getShortMessage());
						newHead = commit.call();
					} else
						newHead = cherryPickResult.getNewHead();
					break;
				}
			} else {
				MergeCommand merge = new Git(repo).merge()
						.setFastForward(MergeCommand.FastForwardMode.NO_FF)
						.setCommit(false);
				for (int i = 1; i < commitToPick.getParentCount(); i++)
					merge.include(newParents.get(i));
				MergeResult mergeResult = merge.call();
				if (mergeResult.getMergeStatus().isSuccessful()) {
					CommitCommand commit = new Git(repo).commit();
					commit.setAuthor(commitToPick.getAuthorIdent());
					commit.setMessage(commitToPick.getFullMessage());
							+ commitToPick.getShortMessage());
					newHead = commit.call();
				} else {
					if (operation == Operation.BEGIN
							&& mergeResult.getMergeStatus() == MergeResult.MergeStatus.FAILED)
						return abort(RebaseResult.failed(mergeResult
								.getFailingPaths()));
					return stop(commitToPick
				}
			}
		}
		return null;
	}

	private void writeMergeInfo(RevCommit commitToPick
			List<RevCommit> newParents) throws IOException {
		repo.writeMergeHeads(newParents.subList(1
		repo.writeMergeCommitMsg(commitToPick.getFullMessage());
	}

	private List<RevCommit> getNewParents(RevCommit commitToPick)
			throws IOException {
		List<RevCommit> newParents = new ArrayList<RevCommit>();
		for (int p = 0; p < commitToPick.getParentCount(); p++) {
			String parentHash = commitToPick.getParent(p).getName();
			if (!new File(rebaseState.getRewrittenDir()
				newParents.add(commitToPick.getParent(p));
			else {
				String newParent = RebaseState.readFile(
						rebaseState.getRewrittenDir()
				if (newParent.length() == 0)
					newParents.add(walk.parseCommit(repo
							.resolve(Constants.HEAD)));
				else
					newParents.add(walk.parseCommit(ObjectId
							.fromString(newParent)));
			}
		}
		return newParents;
	}

	private void writeCurrentCommit(RevCommit commit) throws IOException {
		RebaseState.appendToFile(rebaseState.getFile(CURRENT_COMMIT)
				commit.name());
	}

	private void writeRewrittenHashes() throws RevisionSyntaxException
			IOException {
		File currentCommitFile = rebaseState.getFile(CURRENT_COMMIT);
		if (!currentCommitFile.exists())
			return;

		String head = repo.resolve(Constants.HEAD).getName();
		String currentCommits = rebaseState.readFile(CURRENT_COMMIT);
			RebaseState
					.createFile(rebaseState.getRewrittenDir()
		FileUtils.delete(currentCommitFile);
	}

