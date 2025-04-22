			@Override
			protected Collection<String> computeMessageProposals() {
				return CommitMessageHistory.getCommitHistory();
			}
		};
		commitText = new CommitMessageArea(messageArea, commitMessage, SWT.NONE) {
			@Override
			protected CommitProposalProcessor getCommitProposalProcessor() {
				return commitProposalProcessor;
			}
