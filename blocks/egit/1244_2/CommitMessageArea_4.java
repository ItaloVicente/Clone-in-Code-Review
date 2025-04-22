	public String getCommitMessage() {
		String text = getText();
		text = text.replaceAll(getTextWidget().getLineDelimiter(), "\n"); //$NON-NLS-1$
		boolean shouldHardWrap = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.COMMITTING_HARD_WRAP_MESSAGE);
		if (shouldHardWrap) {
			text = wrap(text, 70);
		}
		return text;
	}


