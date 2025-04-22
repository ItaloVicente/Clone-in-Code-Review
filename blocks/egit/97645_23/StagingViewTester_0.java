
	public int getCaretPosition() {
		SWTBotStyledText commitMessageArea = stagingView.bot().styledTextWithLabel(UIText.StagingView_CommitMessage);
		Integer pos = syncExec(() -> Integer
				.valueOf(commitMessageArea.widget.getCaretOffset()));
		return pos == null ? -1 : pos.intValue();
	}
