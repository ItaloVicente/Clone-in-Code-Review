
			categoryString = UIText.RepositoryPropertySource_RepositoryConfigurationCategory;
			if (repositoryConfig instanceof FileBasedConfig) {
				categoryString += ((FileBasedConfig) repositoryConfig)
						.getFile().getAbsolutePath();
			}
