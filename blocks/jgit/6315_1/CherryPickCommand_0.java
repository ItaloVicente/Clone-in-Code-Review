				if (srcCommit.getParentCount() != 1) {
					if (!mainlineSet)
						throw new MultipleParentsNotAllowedException(
								MessageFormat.format(
										JGitText.get().commitIsAMergeButNoMainlineWasSpecified
										srcCommit.name()
										Integer.valueOf(srcCommit.getParentCount())));
					 else if (mainline >= srcCommit.getParentCount())
						throw new NoSuchParentException(
								MessageFormat.format(
										JGitText.get().invalidCommitParentNumber
										srcCommit.name()
										Integer.valueOf(srcCommit.getParentCount())));
				}

				RevCommit srcParent = srcCommit.getParent(mainline);

