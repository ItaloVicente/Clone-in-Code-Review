	private final CreateRepositoryPage myCreatePage;

	private Repository newRepo;

	public NewRepositoryWizard(boolean hideBareOption) {
		myCreatePage = new CreateRepositoryPage(hideBareOption);
	}
