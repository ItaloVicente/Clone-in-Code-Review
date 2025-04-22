		if (commitMessage.trim().length() == 0) {
			MessageDialog.openWarning(getShell(), UIText.CommitDialog_ErrorNoMessage, UIText.CommitDialog_ErrorMustEnterCommitMessage);
			return;
		}

		boolean authorValid = false;
		if (author.length() > 0) {
			authorValid = RawParseUtils.parsePersonIdent(author) != null;
		}
		if (!authorValid) {
			MessageDialog.openWarning(getShell(), UIText.CommitDialog_ErrorInvalidAuthor, UIText.CommitDialog_ErrorInvalidAuthorSpecified);
			return;
		}

		boolean committerValid = false;
		if (committer.length() > 0) {
			committerValid = RawParseUtils.parsePersonIdent(committer) != null;
		}
		if (!committerValid) {
			MessageDialog.openWarning(getShell(), UIText.CommitDialog_ErrorInvalidAuthor, UIText.CommitDialog_ErrorInvalidCommitterSpecified);
			return;
		}
