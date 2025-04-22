			RefDirectory refDir = new RefDirectory(this);
			refs = refDir;
			if (repoConfig.getBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_REFCACHE
				RefCache refCache = new InMemoryRefDatabase(refDir);
				refDir.setRefCache(Optional.of(refCache));
			}
