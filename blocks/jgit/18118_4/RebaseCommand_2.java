	private RebaseResult processStep(RebaseTodoLine step
			throws IOException
		if (Action.COMMENT.equals(step.getAction()))
			return null;
		ObjectReader or = repo.newObjectReader();

		Collection<ObjectId> ids = or.resolve(step.getCommit());
		if (ids.size() != 1)
			throw new JGitInternalException(
					"Could not resolve uniquely the abbreviated object ID");
		RevCommit commitToPick = walk.parseCommit(ids.iterator().next());
		if (shouldPick) {
			if (monitor.isCancelled())
				return new RebaseResult(commitToPick
			RebaseResult result = cherryPickCommit(commitToPick);
			if (result != null) {
				return result;
			}

		}
		boolean isSquash = false;
		switch (step.getAction()) {
		case PICK:
		case REWORD:
			String oldMessage = commitToPick.getFullMessage();
			String newMessage = interactiveHandler
					.modifyCommitMessage(oldMessage);
			newHead = new Git(repo).commit().setMessage(newMessage)
					.setAmend(true).call();
			return null;
		case EDIT:
			rebaseState.createFile(AMEND
			return stop(commitToPick
		case COMMENT:
			break;
		case SQUASH:
			isSquash = true;
		case FIXUP:
			resetSoftToParent();
			List<RebaseTodoLine> steps = repo.readRebaseTodo(
					rebaseState.getPath(GIT_REBASE_TODO)
			RebaseTodoLine nextStep = steps.size() > 0 ? steps.get(0) : null;
			File messageFixupFile = rebaseState.getFile(MESSAGE_FIXUP);
			File messageSquashFile = rebaseState.getFile(MESSAGE_SQUASH);
			if (isSquash && messageFixupFile.exists())
				messageFixupFile.delete();
			newHead = doSquashFixup(isSquash
					messageFixupFile
		}
		return null;
	}

	private RebaseResult cherryPickCommit(RevCommit commitToPick)
			throws IOException
			UnmergedPathsException
			WrongRepositoryStateException
		try {
			monitor.beginTask(MessageFormat.format(
					JGitText.get().applyingCommit
					commitToPick.getShortMessage())
			newHead = tryFastForward(commitToPick);
			lastStepWasForward = newHead != null;
			if (!lastStepWasForward) {
				String ourCommitName = getOurCommitName();
				CherryPickResult cherryPickResult = new Git(repo).cherryPick()
						.include(commitToPick).setOurCommitName(ourCommitName)
				switch (cherryPickResult.getStatus()) {
				case FAILED:
					if (operation == Operation.BEGIN)
						return abort(new RebaseResult(
								cherryPickResult.getFailingPaths()));
					else
						return stop(commitToPick
				case CONFLICTING:
					return stop(commitToPick
				case OK:
					newHead = cherryPickResult.getNewHead();
				}
			}
			return null;
		} finally {
			monitor.endTask();
		}
	}

