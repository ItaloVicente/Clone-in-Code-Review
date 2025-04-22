			category = NLS
					.bind(
							UIText.RepositoryPropertySource_RepositoryConfigurationCategory,
							location);
			config = repositoryConfig;
			break;
		}
		case USER: {
			prefix = USER_ID_PREFIX;
			String location = ""; //$NON-NLS-1$
			if (userHomeConfig instanceof FileBasedConfig) {
				location = ((FileBasedConfig) userHomeConfig).getFile()
						.getAbsolutePath();
