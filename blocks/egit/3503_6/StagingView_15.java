	private boolean userEnteredCommmitMessage() {
		if (commitMessageComponent.getRepository() == null)
			return false;
		String message = commitMessageComponent.getCommitMessage();
		if (message == null || message.trim().length() == 0)
			return false;
		return true;
	}
