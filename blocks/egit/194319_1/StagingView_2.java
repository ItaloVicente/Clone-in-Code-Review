	private void setCommentCharTooltip() {
		if (previewLayout.topControl != commitMessageText) {
			commitMessageSection.setToolTipText(null);
			return;
		}
		switch (commitMessageText.getCleanupMode()) {
		case STRIP:
		case SCISSORS:
			commitMessageSection.setToolTipText(MessageFormat.format(
					UIText.StagingView_CommentChar,
					String.valueOf(commitMessageText.getCommentChar())));
			break;
		default:
			commitMessageSection.setToolTipText(null);
			break;
		}
	}

