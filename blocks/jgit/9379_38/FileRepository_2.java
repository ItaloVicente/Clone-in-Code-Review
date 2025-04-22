		SymLinks symLinks = SymLinks.FALSE;
		if (getFS().supportsSymlinks()) {
			File tmp = new File(getDirectory()
			try {
				getFS().createSymLink(tmp
				symLinks = null;
				FileUtils.delete(tmp);
			} catch (IOException e) {
			}
		}
		if (symLinks != null)
			cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SYMLINKS
							.toLowerCase());
