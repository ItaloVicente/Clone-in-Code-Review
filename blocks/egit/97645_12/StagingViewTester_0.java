
	public int getCaretPosition() {
		SWTBotStyledText commitMessageArea = stagingView.bot().styledTextWithLabel(UIText.StagingView_CommitMessage);
		Position cursorPosition = commitMessageArea.cursorPosition();
		List<String> lines = commitMessageArea.getLines();

		int caretPosition = 0;
		for (int i = 0; i <= cursorPosition.line; i++) {
			if (i < cursorPosition.line) {
				caretPosition += lines.get(i).length();
			} else {
				caretPosition += cursorPosition.column;
			}
		}

		return caretPosition;
	}
