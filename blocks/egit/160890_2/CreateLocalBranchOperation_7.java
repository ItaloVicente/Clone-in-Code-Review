			if (upstreamConfig != null) {
				StoredConfig config = repository.getConfig();
				config.setEnum(ConfigConstants.CONFIG_BRANCH_SECTION, name,
						ConfigConstants.CONFIG_KEY_REBASE, upstreamConfig);
				try {
					config.save();
				} catch (IOException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
