		if (remoteConfig == null)
			predefinedConfigured = Collections.emptyList();
		else {
			if (pushSpecs)
				predefinedConfigured = remoteConfig.getPushRefSpecs();
			else
				predefinedConfigured = remoteConfig.getFetchRefSpecs();
			for (final RefSpec spec : predefinedConfigured)
				addRefSpec(spec);
