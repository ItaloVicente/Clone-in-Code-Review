	@Test
	public void shareProjectWithAlreadyCreatedRepos() throws IOException, InterruptedException {
		FileRepository repo1 = new FileRepository(new File(createProject(projectName1), "../.git"));
		repo1.create();
		repo1.close();
		FileRepository repo2 = new FileRepository(new File(createProject(projectName2), ".git"));
		repo2.create();
		repo2.close();
		ExistingOrNewPage existingOrNewPage = sharingWizard
				.openWizard(projectName1, projectName2);
