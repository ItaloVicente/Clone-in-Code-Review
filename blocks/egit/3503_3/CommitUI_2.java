		commitDialog.setAuthor(commitHelper.getAuthor());
		commitDialog.setCommitter(commitHelper.getCommitter());
		commitDialog.setAllowToChangeSelection(!commitHelper.isMergedResolved && !commitHelper.isCherryPickResolved);
		commitDialog.setPreviousCommitMessage(commitHelper.getPreviousCommitMessage());
		commitDialog.setPreviousAuthor(commitHelper.getPreviousAuthor());
		commitDialog.setCommitMessage(commitHelper.getCommitMessage());
