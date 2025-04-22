				if (srcCommit.getParentCount() != 1) {
					if (!mainlineSet)
						throw new MultipleParentsNotAllowedException(
								JGitText.get().commitIsAMergeButNoMainlineWasSpecified);
					 else if (mainline >= srcCommit.getParentCount())
						throw new NoSuchParentException(
								JGitText.get().invalidCommitParentNumber);
				}

				RevCommit srcParent = srcCommit.getParent(mainline);

