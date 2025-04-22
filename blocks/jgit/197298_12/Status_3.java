			if (!porcelain) {
			}
		}
		if (nbUntracked > 0 && nbToBeCommitted == 0 &&
			printHint(CLIText.get().hintsNothingAddedToCommitBut);
		}
		if (nbUntracked > 0 && nbToBeCommitted == 0 &&
				("no".equals(untrackedFilesMode)) && nbUnmerged == 0
			printHint(CLIText.get().hintsUntrackedFilesNotListed);
		}
		if (nbToBeCommitted == 0 && nbNotStagedForCommit == 0
				&& nbUnmerged == 0 && nbUntracked == 0 &&
			if (head != null && !porcelain) {
				assert head != null;
				if (head.getObjectId() != null) {
					printHint(CLIText.get().hintsNothingToCommit);
				} else {
					printHint(CLIText.get().hintsNothingToCommitCreateCopy);
				}
			}
