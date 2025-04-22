		} else // repository did not change
		if (userEnteredCommmitMessage()) {
			if (!commitMessageComponent.getHeadCommit().equals(
					helper.getPreviousCommit()))
				addHeadChangedWarning(commitMessageComponent
						.getCommitMessage());
		} else
			loadInitialState(helper);
