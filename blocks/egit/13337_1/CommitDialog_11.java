		commitText = new CommitMessageArea(messageArea, commitMessage, SWT.NONE) {
			@Override
			protected CommitProposalProcessor getCommitProposalProcessor() {
				return commitProposalProcessor;
			}
		};
		commitText
				.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		messageSection.setClient(messageArea);
		Point size = commitText.getTextWidget().getSize();
		int minHeight = commitText.getTextWidget().getLineHeight() * 3;
		commitText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, true).hint(size).minSize(size.x, minHeight)
				.align(SWT.FILL, SWT.FILL).create());
