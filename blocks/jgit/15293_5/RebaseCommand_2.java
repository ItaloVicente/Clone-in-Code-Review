	private void checkSteps(List<RebaseTodoLine> steps) throws IOException {
		if (steps.isEmpty())
			return;
		if (RebaseTodoLine.Action.SQUASH.equals(steps.get(0).getAction())
				|| RebaseTodoLine.Action.FIXUP.equals(steps.get(0).getAction())) {
			if (!rebaseState.getFile(DONE).exists()
					|| rebaseState.readFile(DONE).trim().length() == 0) {
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().cannotSquashFixupWithoutPreviousCommit
						steps.get(0).getAction().name()));
			}
		}

	}

	private RevCommit doSquashFixup(boolean isSquash
			RebaseTodoLine nextStep
			throws IOException

		if (!messageSquash.exists()) {
			ObjectId headId = repo.resolve(Constants.HEAD);
			RevCommit previousCommit = walk.parseCommit(headId);

			initializeSquashFixupFile(MESSAGE_SQUASH
					previousCommit.getFullMessage());
			if (!isSquash)
				initializeSquashFixupFile(MESSAGE_FIXUP
					previousCommit.getFullMessage());
		}
		String currSquashMessage = rebaseState
				.readFile(MESSAGE_SQUASH);

		int count = parseSquashFixupSequenceCount(currSquashMessage) + 1;

		String content = composeSquashMessage(isSquash
				commitToPick
		rebaseState.createFile(MESSAGE_SQUASH
		if (messageFixup.exists()) {
			rebaseState.createFile(MESSAGE_FIXUP
		}

		return squashIntoPrevious(
				!messageFixup.exists()
				nextStep);
	}

	private void resetSoftToParent() throws IOException
			GitAPIException
		Ref orig_head = repo.getRef(Constants.ORIG_HEAD);
		ObjectId orig_headId = orig_head.getObjectId();
		try {
			Git.wrap(repo).reset().setMode(ResetType.SOFT)
		} finally {
			repo.writeOrigHead(orig_headId);
		}
	}

	private RevCommit squashIntoPrevious(boolean sequenceContainsSquash
			RebaseTodoLine nextStep)
			throws IOException
		RevCommit newHead;
		String commitMessage = rebaseState
				.readFile(MESSAGE_SQUASH);

		if (nextStep == null
				|| ((nextStep.getAction() != Action.FIXUP) && (nextStep
						.getAction() != Action.SQUASH))) {
			if (sequenceContainsSquash) {
				commitMessage = interactiveHandler
						.modifyCommitMessage(commitMessage);
			}
			newHead = new Git(repo).commit()
					.setMessage(stripCommentLines(commitMessage))
					.setAmend(true).call();
			rebaseState.getFile(MESSAGE_SQUASH).delete();
			rebaseState.getFile(MESSAGE_FIXUP).delete();

		} else {
			newHead = new Git(repo).commit()
					.setMessage(commitMessage).setAmend(true)
					.call();
		}
		return newHead;
	}

	private static String stripCommentLines(String commitMessage) {
		StringBuilder result = new StringBuilder();
			}
		}
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	@SuppressWarnings("nls")
	private static String composeSquashMessage(boolean isSquash
			RevCommit commitToPick
		StringBuilder sb = new StringBuilder();
		String ordinal = getOrdinal(count);
		sb.setLength(0);
		sb.append("# This is a combination of ").append(count)
				.append(" commits.\n");
		if (isSquash) {
			sb.append("# This is the ").append(count).append(ordinal)
					.append(" commit message:\n");
			sb.append(commitToPick.getFullMessage());
		} else {
			sb.append("# The ").append(count).append(ordinal)
					.append(" commit message will be skipped:\n# ");
			sb.append(commitToPick.getFullMessage().replaceAll("([\n\r]+)"
					"$1# "));
		}
		sb.append("\n");
		sb.append(currSquashMessage.substring(currSquashMessage.indexOf("\n") + 1));
		return sb.toString();
	}

	private static String getOrdinal(int count) {
		switch (count % 10) {
		case 1:
		case 2:
		case 3:
		default:
		}
	}

	static int parseSquashFixupSequenceCount(String currSquashMessage) {
		String firstLine = currSquashMessage.substring(0
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(firstLine);
		if (!matcher.find())
			throw new IllegalArgumentException();
		return Integer.parseInt(matcher.group(1));
	}

	private void initializeSquashFixupFile(String messageFile
			String fullMessage) throws IOException {
		rebaseState
				.createFile(
						messageFile
	}

