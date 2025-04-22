	public B setInitialBranch(String branch) throws InvalidRefNameException {
		if (!Repository.isValidRefName(Constants.R_HEADS + branch)) {
			throw new InvalidRefNameException(MessageFormat
					.format(JGitText.get().branchNameInvalid
		}
		this.initialBranch = branch;
		return self();
	}

	public String getInitialBranch() {
		return initialBranch;
	}

