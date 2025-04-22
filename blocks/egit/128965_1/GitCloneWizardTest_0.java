	@Test
	public void testFileURIWithSpace() throws Exception {
		importWizard.openWizard();
		RepoPropertiesPage propertiesPage = importWizard
				.openRepoPropertiesPage();
		propertiesPage.setURI("file:///Some/Directory With Spaces/bare.git");
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			propertiesPage.assertSourceParams(" "
					+ System.getProperty("user.dir")
					+ "\\.\\Some\\Directory With Spaces\\bare.git does not exist.",
					"", "/Some/Directory With Spaces/bare.git", "file", "",
					false, "", "", false, false);
		} else {
			propertiesPage.assertSourceParams(
					" /Some/Directory With Spaces/bare.git does not exist.", "",
					"/Some/Directory With Spaces/bare.git", "file", "", false,
					"", "", false, false);
		}
		bot.button("Cancel").click();
	}

