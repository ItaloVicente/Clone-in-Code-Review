			DETECTED = new FS_POSIX_Java5();
	}

	private final File userHome;

	protected FS() {
		this.userHome = userHomeImpl();
