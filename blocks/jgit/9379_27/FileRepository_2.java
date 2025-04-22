		SymLinks symLinks = SymLinks.FALSE;
		if (getFS().supportsSymlinks()) {
			File tmp = new File(getDirectory()
			try {
				getFS().createSymLink(tmp
				symLinks = null;
			} finally {
				FileUtils.delete(tmp);
			}
		}
		if (symLinks != null)
			cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SYMLINKS
							.toLowerCase());
