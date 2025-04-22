		if (configuredConfig != null)
			return configuredConfig;

		if (defaultConfig != null)
			if (!defaultConfig.getPushRefSpecs().isEmpty())
				return defaultConfig;

		return null;
