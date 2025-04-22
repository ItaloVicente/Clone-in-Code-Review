	@Test
	public void testSearchDirectoryWithBareRepos() throws Exception {
		deleteAllProjects();
		clearView();
		refreshAndWait();
		assertEmpty();
		getOrOpenView()
				.toolbarButton(
						myUtil.getPluginLocalizedValue("RepoViewAddRepository.tooltip"))
				.click();

		Git.init().setBare(true)
				.setDirectory(new File(getTestDirectory(), "BareRepository1"))
				.call();

		Git.init().setBare(true)
				.setDirectory(new File(getTestDirectory(), "BareRepository2"))
				.call();

		SWTBotShell shell = bot.shell(
				UIText.RepositorySearchDialog_AddGitRepositories).activate();

		shell.bot().checkBox(UIText.RepositorySearchDialog_DeepSearch_button)
				.deselect();

		shell.bot().textWithLabel(UIText.RepositorySearchDialog_directory)
				.setText(getTestDirectory().getPath());

		shell.bot().button(UIText.RepositorySearchDialog_Search).click();

		int max = 5000;
		int slept = 0;
		while (ModalContext.getModalLevel() > 0 && slept < max) {
			Thread.sleep(100);
			slept += 100;
		}

		assertEquals(3, shell.bot().tree().rowCount());
	}

