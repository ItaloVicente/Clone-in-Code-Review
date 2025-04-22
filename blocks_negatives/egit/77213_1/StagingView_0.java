			if (!commitMessageComponent.getHeadCommit().equals(
					helper.getPreviousCommit())) {
				if (!commitMessageComponent.isAmending()
						&& userEnteredCommitMessage())
					addHeadChangedWarning(commitMessageComponent
							.getCommitMessage());
				else
					loadInitialState(helper);
