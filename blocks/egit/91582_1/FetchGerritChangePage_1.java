	private CheckoutResult checkout(final String textForBranch)
			throws GitAPIException {
		CheckoutCommand co = null;
		try (Git git = new Git(repository)) {
			co = git.checkout();
			co.setName(textForBranch).call();
		} catch (CheckoutConflictException e) {
		}
		return co.getResult();
	}

