
	public InitCommand setInitialBranch(String branch)
			throws InvalidRefNameException {
		if (StringUtils.isEmptyOrNull(branch)) {
			this.initialBranch = Constants.MASTER;
		} else {
			if (!Repository.isValidRefName(Constants.R_HEADS + branch)) {
				throw new InvalidRefNameException(MessageFormat
						.format(JGitText.get().branchNameInvalid
			}
			this.initialBranch = branch;
		}
		return this;
	}
