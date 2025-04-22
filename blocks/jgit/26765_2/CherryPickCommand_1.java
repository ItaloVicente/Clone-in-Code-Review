	private RevCommit getParentCommit(RevCommit srcCommit
			throws MultipleParentsNotAllowedException
			IOException {
		final RevCommit srcParent;
		if (mainlineParentNumber == null) {
			if (srcCommit.getParentCount() != 1)
				throw new MultipleParentsNotAllowedException(
						MessageFormat.format(
								JGitText.get().canOnlyCherryPickCommitsWithOneParent
								srcCommit.name()
								Integer.valueOf(srcCommit.getParentCount())));
			srcParent = srcCommit.getParent(0);
		} else {
			if (mainlineParentNumber.intValue() > srcCommit.getParentCount())
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().commitDoesNotHaveGivenParent
						mainlineParentNumber));
			srcParent = srcCommit
					.getParent(mainlineParentNumber.intValue() - 1);
		}

		revWalk.parseHeaders(srcParent);
		return srcParent;
	}

