
		if (config.getFetchRefSpecs().isEmpty()
				&& !repository.getConfig()
						.getSubsections(ConfigConstants.CONFIG_REMOTE_SECTION)
						.contains(config.getName())) {
			StringBuilder defaultRefSpec = new StringBuilder();
			defaultRefSpec.append('+');
			defaultRefSpec.append(Constants.R_HEADS);
			defaultRefSpec.append('*').append(':');
			defaultRefSpec.append(Constants.R_REMOTES);
			defaultRefSpec.append(config.getName());
			defaultRefSpec.append(RefSpec.WILDCARD_SUFFIX);
			config.addFetchRefSpec(new RefSpec(defaultRefSpec.toString()));
		}
