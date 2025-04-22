		final long repositoryFormatVersion = getConfig().getLong(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION

		String reftype = repoConfig.getString(
				"extensions"
		if (repositoryFormatVersion >= 1 && reftype != null) {
			if (StringUtils.equalsIgnoreCase(reftype
				RefTreeDb.BootstrapBehavior behavior = getConfig().getEnum(
						"reftree"
						RefTreeDb.BootstrapBehavior.UNION);
				refs = new RefTreeDb(this
			} else {
				throw new IOException(JGitText.get().unknownRepositoryFormat);
			}
		} else {
			refs = new RefDirectory(this);
		}

