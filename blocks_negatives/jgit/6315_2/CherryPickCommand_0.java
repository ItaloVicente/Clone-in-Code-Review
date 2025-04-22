				if (srcCommit.getParentCount() != 1)
					throw new MultipleParentsNotAllowedException(
							MessageFormat.format(
									JGitText.get().canOnlyCherryPickCommitsWithOneParent,
									srcCommit.name(),
									Integer.valueOf(srcCommit.getParentCount())));

				RevCommit srcParent = srcCommit.getParent(0);
