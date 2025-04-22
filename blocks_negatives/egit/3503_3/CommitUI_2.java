		commitDialog.setAuthor(author);
		commitDialog.setCommitter(committer);
		commitDialog.setAllowToChangeSelection(!isMergedResolved && !isCherryPickResolved);

		if (previousCommit != null) {
			commitDialog.setPreviousCommitMessage(previousCommit.getFullMessage());
			PersonIdent previousAuthor = previousCommit.getAuthorIdent();
			commitDialog.setPreviousAuthor(previousAuthor.getName()
		}
		if (isMergedResolved || isCherryPickResolved) {
			commitDialog.setCommitMessage(getMergeResolveMessage(mergeRepository));
		}

		if (isCherryPickResolved) {
			commitDialog.setAuthor(getCherryPickOriginalAuthor(mergeRepository));
		}
