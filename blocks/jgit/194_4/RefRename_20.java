	protected RefRename(final RefUpdate src
		source = src;
		destination = dst;

		Repository repo = destination.getRepository();
		String cmd = "";
		if (source.getName().startsWith(Constants.R_HEADS)
				&& destination.getName().startsWith(Constants.R_HEADS))
			cmd = "Branch: ";
		setRefLogMessage(cmd + "renamed "
				+ repo.shortenRefName(source.getName()) + " to "
				+ repo.shortenRefName(destination.getName()));
	}

	public PersonIdent getRefLogIdent() {
		return destination.getRefLogIdent();
	}

	public void setRefLogIdent(final PersonIdent pi) {
		destination.setRefLogIdent(pi);
	}

	public String getRefLogMessage() {
		return destination.getRefLogMessage();
	}

	public void setRefLogMessage(final String msg) {
		if (msg == null)
			disableRefLog();
		else
			destination.setRefLogMessage(msg
	}

	public void disableRefLog() {
		destination.setRefLogMessage(""
