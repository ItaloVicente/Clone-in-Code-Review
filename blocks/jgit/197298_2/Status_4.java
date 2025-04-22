			if (!porcelain) {
				outw.println("");
			}
		}
		if (nbUntracked > 0 && nbToBeCommitted == 0 &&
			printHint(CLIText.get().hintsNothingAddedToCommitBut
					false
		}
		if (nbUntracked > 0 && nbToBeCommitted == 0 &&
				("no".equals(untrackedFilesMode)) && nbUnmerged == 0) {
			printHint(CLIText.get().hintsUntrackedFilesNotListed
					false
		}
		if (nbToBeCommitted == 0 && nbNotStagedForCommit == 0
				&& nbUnmerged == 0 && nbUntracked == 0 &&
			if (head.getObjectId() != null) {
				printHint(CLIText.get().hintsNothingToCommit
						false
			} else {
				printHint(CLIText.get().hintsNothingToCommitCreateCopy
						false
			}
