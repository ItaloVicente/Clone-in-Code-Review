		List<Ref> tags = cloned.getRefDatabase()
				.getRefsByPrefix(Constants.R_TAGS);
		Assert.assertFalse(tags.isEmpty());
	}

	@Test
	public void cloneWithTagOption() throws Exception {
		destRepo = new File(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
				"test1");
		tagOptionToSelect = TagOpt.NO_TAGS;

		importWizard.openWizard();
		RepoPropertiesPage propertiesPage = importWizard
				.openRepoPropertiesPage();

		RepoRemoteBranchesPage remoteBranches = propertiesPage
				.nextToRemoteBranches(r.getUri());

		Repository cloned = cloneRepo(destRepo, remoteBranches);
		bot.button("Cancel").click();
		List<Ref> tags = cloned.getRefDatabase()
				.getRefsByPrefix(Constants.R_TAGS);
		Assert.assertTrue(tags.isEmpty());
