			if (!commitMessageComponent.getHeadCommit()
					.equals(helper.getPreviousCommit())
					&& !commitMessageComponent.isAmending()
					&& userEnteredCommitMessage()) {
				addHeadChangedWarning(
						commitMessageComponent.getCommitMessage());
