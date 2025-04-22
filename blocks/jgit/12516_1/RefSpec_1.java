	private void setSourceWithValidation(final String source) {
		if (source != null && !isValid(source))
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidRefSpec
		srcName = source;
	}

