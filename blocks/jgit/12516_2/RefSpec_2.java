	private void setDestinationWithValidation(final String destination) {
		if (destination != null && !isValid(destination))
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidRefSpec
		dstName = destination;
	}

