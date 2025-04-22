		final CommitProposalProcessor commitProposalProcessor = new CommitProposalProcessor() {
			@Override
			protected Collection<String> computeFileNameProposals() {
				return getStagedFileNames();
			}

			@Override
			protected Collection<String> computeMessageProposals() {
				return CommitMessageHistory.getCommitHistory();
			}
		};
		commitMessageText = new CommitMessageArea(
				commitMessageComposite, EMPTY_STRING, toolkit.getBorderStyle()) {
			@Override
			protected CommitProposalProcessor getCommitProposalProcessor() {
				return commitProposalProcessor;
			}
		};
