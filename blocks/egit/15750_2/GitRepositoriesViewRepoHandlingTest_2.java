		getOrOpenView()
				.toolbarButton(
						myUtil.getPluginLocalizedValue("RepoViewAddRepository.tooltip"))
				.click();

		SWTBotShell shell = bot
				.shell(UIText.RepositorySearchDialog_AddGitRepositories);
