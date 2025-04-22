	public CommitCommand setNoVerify(boolean noVerify) {
		this.noVerify = noVerify;
		return this;
	}

	private void commitRejectedByHook(Hook cause
			throws RejectedCommitException {
		String errorMessage = MessageFormat.format(
				JGitText.get().commitRejectedByHook
				errorDetails);
		throw new RejectedCommitException(errorMessage);
	}
