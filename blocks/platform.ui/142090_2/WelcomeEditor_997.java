	}

	protected StyledText getCurrentText() {
		return currentText;
	}

	protected WelcomeEditorCopyAction getCopyAction() {
		return copyAction;
	}

	private StyleRange findNextLink(StyledText text) {
		if (text == null) {
