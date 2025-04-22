	/**
	 * @param repository
	 *            the repository
	 * @param baseBranch
	 *            the base branch, may be null
	 */
	public CreateBranchWizard(Repository repository, Ref baseBranch) {
		myPage = new CreateBranchPage(repository, baseBranch);
		setWindowTitle(UIText.CreateBranchWizard_NewBranchTitle);
	}
