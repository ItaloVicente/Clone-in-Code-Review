		return checkout(repository, target, true);
	}

	public static BranchOperationUI checkout(Repository repository,
			String target, boolean showQuestionsBeforeCheckout) {
		return new BranchOperationUI(new Repository[] { repository }, target,
				showQuestionsBeforeCheckout);
