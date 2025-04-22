		String targetDir = bot.textWithLabel("Directory:").getText()
				+ File.separatorChar + Constants.DOT_GIT;
		assertFalse(
				"Clone target should not be in the configured repositories list",
				Activator.getDefault().getRepositoryUtil()
						.getConfiguredRepositories().contains(targetDir));
