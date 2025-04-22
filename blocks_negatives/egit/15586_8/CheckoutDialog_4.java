		try {
			currentBranch = repo.getFullBranch();
		} catch (IOException e) {
			currentBranch = null;
		}
	}

	@Override
	protected String getMessageText() {
		return UIText.CheckoutDialog_Message;
