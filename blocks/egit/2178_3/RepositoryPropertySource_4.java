		case SYSTEM: {
			prefix = SYSTEM_ID_PREFIX;
			String location = systemConfig.getFile().getAbsolutePath();
			category = NLS
					.bind(UIText.RepositoryPropertySource_GlobalConfigurationCategory,
							location);
			config = systemConfig;
			break;
		}
