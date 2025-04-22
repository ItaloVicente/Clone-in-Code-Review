	public String getCommitMessage() {
		String text = getText();
		text = text.replaceAll(getTextWidget().getLineDelimiter(), "\n"); //$NON-NLS-1$
		return text;
	}

	public void reconfigure() {
		configureHardWrap();
	}

