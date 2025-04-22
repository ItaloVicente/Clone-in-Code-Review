		getTextWidget().setFont(UIUtils
				.getFont(UIPreferences.THEME_CommitMessageEditorFont));

		int endSpacing = 2;
		int textWidth = getCharWidth() * 70 + endSpacing;
		int textHeight = getLineHeight() * 7;
		Point size = getTextWidget().computeSize(textWidth, textHeight);
		getTextWidget().setSize(size);
