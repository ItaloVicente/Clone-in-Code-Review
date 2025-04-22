	private final CreateRepositoryPage myCreatePage;

	private Repository newRepo;

	/**
	 * @param hideBareOption
	 *            if <code>true</code>, no "bare" repository can be created
	 */
	public NewRepositoryWizard(boolean hideBareOption) {
		myCreatePage = new CreateRepositoryPage(hideBareOption);
	}
