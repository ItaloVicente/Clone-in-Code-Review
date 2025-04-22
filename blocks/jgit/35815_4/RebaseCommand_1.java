	private void rebaseRejectedByHook(Hook cause
			throws RejectedRebaseException {
		String errorMessage = MessageFormat.format(
				JGitText.get().rebaseRejectedByHook
		if (errorDetails.length() > 0)
			errorMessage += '\n' + errorDetails;
		throw new RejectedRebaseException(errorMessage);
	}

