
	@Override
	protected File userHomeImpl() {
		String home = SystemReader.getInstance().getenv("HOME");
		if (home != null)
			return resolve(null
		String homeDrive = SystemReader.getInstance().getenv("HOMEDRIVE");
		if (homeDrive != null) {
			String homePath = SystemReader.getInstance().getenv("HOMEPATH");
			return new File(homeDrive
		}

		String homeShare = SystemReader.getInstance().getenv("HOMESHARE");
		if (homeShare != null)
			return new File(homeShare);

		return super.userHomeImpl();
	}
