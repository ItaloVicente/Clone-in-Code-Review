	public String getCommitMessage() {
		String text = getText();
		text = text.replaceAll(getTextWidget().getLineDelimiter(), "\n"); //$NON-NLS-1$
		text = wrap(text, 70);
		return text;
	}


