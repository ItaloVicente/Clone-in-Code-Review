	private File safeUserHomeImpl() {
		File home;
		try {
			home = userHomeImpl();
			if (home != null) {
				home.toPath();
				return home;
			}
		} catch (RuntimeException e) {
			LOG.error(JGitText.get().exceptionWhileFindingUserHome
		}
		home = defaultUserHomeImpl();
		if (home != null) {
			try {
				home.toPath();
				return home;
			} catch (InvalidPathException e) {
				LOG.error(MessageFormat
						.format(JGitText.get().invalidHomeDirectory
			}
		}
		return null;
	}

