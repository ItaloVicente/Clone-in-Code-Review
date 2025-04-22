	public int getMessageType() {
		String authorValue = authorText.getText();
		if (authorValue.length() == 0
				|| RawParseUtils.parsePersonIdent(authorValue) == null)
			return ERROR;

		String committerValue = committerText.getText();
		if (committerValue.length() == 0
				|| RawParseUtils.parsePersonIdent(committerValue) == null)
			return ERROR;

		if (amending && amendingCommitInRemoteBranch)
			return WARNING;

		return NONE;
	}
