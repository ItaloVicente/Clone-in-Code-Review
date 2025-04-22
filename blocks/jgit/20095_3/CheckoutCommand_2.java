
		if (orphan) {
			Ref refToCheck = repo.getRef(getBranchName());
			if (refToCheck != null)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadyExists
		}
	}

	private String getBranchName() {
		if (name.startsWith(Constants.R_REFS))
			return name;

		return Constants.R_HEADS + name;
