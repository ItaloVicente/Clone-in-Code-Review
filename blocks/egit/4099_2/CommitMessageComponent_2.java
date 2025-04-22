	public String getMessage() {
		if (commitText.getText().trim().length() == 0)
			return UIText.CommitDialog_Message;

		String authorValue = authorText.getText();
		if (authorValue.length() == 0
				|| RawParseUtils.parsePersonIdent(authorValue) == null)
			return UIText.CommitMessageComponent_MessageInvalidAuthor;

		String committerValue = committerText.getText();
		if (committerValue.length() == 0
				|| RawParseUtils.parsePersonIdent(committerValue) == null)
			return UIText.CommitMessageComponent_MessageInvalidCommitter;

		return null;
	}

