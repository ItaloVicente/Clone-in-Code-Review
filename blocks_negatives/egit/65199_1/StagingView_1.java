		commitMessageText = new CommitMessageArea(commitMessageTextComposite,
				EMPTY_STRING, toolkit.getBorderStyle()) {
			@Override
			protected CommitProposalProcessor getCommitProposalProcessor() {
				return commitProposalProcessor;
			}
			@Override
			protected IHandlerService getHandlerService() {
				return CommonUtils.getService(getSite(), IHandlerService.class);
			}
		};
