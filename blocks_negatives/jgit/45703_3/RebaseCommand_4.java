		if (nextStep == null
				|| ((nextStep.getAction() != Action.FIXUP) && (nextStep
						.getAction() != Action.SQUASH))) {
			if (sequenceContainsSquash) {
				commitMessage = interactiveHandler
						.modifyCommitMessage(commitMessage);
			}
			retNewHead = new Git(repo).commit()
					.setMessage(stripCommentLines(commitMessage))
					.setAmend(true).setNoVerify(true).call();
			rebaseState.getFile(MESSAGE_SQUASH).delete();
			rebaseState.getFile(MESSAGE_FIXUP).delete();
