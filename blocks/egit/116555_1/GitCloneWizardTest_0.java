	@Test
	public void gitStyleUriSelectsSshProtocol() throws Exception {
		importWizard.openWizard();

		RepoPropertiesPage propertiesPage = importWizard
				.openRepoPropertiesPage();

		propertiesPage.setURI("git@github.com:someone/somerepo.git");
		propertiesPage.assertSourceParams(null, "github.com",
				"someone/somerepo.git", "ssh", "", true, "git", "", true,
				true);
	}
