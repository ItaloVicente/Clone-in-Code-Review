
				if (UpstreamConfig.REBASE == upstreamConfig) {
					StoredConfig config = repository.getConfig();
					config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION,
							name, ConfigConstants.CONFIG_KEY_REBASE, true);
					try {
						config.save();
					} catch (IOException e) {
						throw new CoreException(Activator.error(e.getMessage(),
								e));
					}
				}
