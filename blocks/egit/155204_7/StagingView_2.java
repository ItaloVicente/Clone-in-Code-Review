
		if (helper.shouldUseCommitTemplate()) {
			commitMessageComponent
					.setCommitMessage(helper.getCommitTemplate());
		} else {
			commitMessageComponent.setCommitMessage(helper.getCommitMessage());
		}

