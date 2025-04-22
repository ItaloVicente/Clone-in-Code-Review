	@Test
	public void acceptCloneCommandAsURI() throws Exception {

		importWizard.openWizard();
		RepoPropertiesPage propertiesPage = importWizard
				.openRepoPropertiesPage();

		propertiesPage.setURI("git clone git://www.jgit.org/EGIT");
		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT", "git",
				"", true, "", "", false, false);
		bot.button("Cancel").click();
	}

