
		if (helper.shouldUseCommitTemplate()) {
			commitMessageComponent
					.setCommitMessage(helper.getCommitTemplate().get());
		} else {
			commitMessageComponent.setCommitMessage(helper.getCommitMessage());
		}

