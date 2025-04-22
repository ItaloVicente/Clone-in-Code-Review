			File userConfig = new File(home
			try {
				userConfig.canRead();
			} catch (AccessControlException e) {
				LOG.warn(MessageFormat.format(JGitText.get().unableToAccessUserConfig
						userConfig.getAbsolutePath()));
				return new FileBasedConfig(parent
					public void load() {
					}

					public boolean isOutdated() {
						return false;
					}
				};
			}
			return new FileBasedConfig(parent
