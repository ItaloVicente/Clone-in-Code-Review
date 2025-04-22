
	private boolean canPushHeadOnly() {
		Repository repo = currentRepository;
		try {
			return repo != null && repo.resolve(Constants.HEAD) != null
					&& !isCommitPossible();
		} catch (IOException e) {
			return false;
		}
	}
