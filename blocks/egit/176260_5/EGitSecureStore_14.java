	private static final EGitSecureStore INSTANCE = new EGitSecureStore(
			SecurePreferencesFactory.getDefault());

	public static EGitSecureStore getInstance() {
		return INSTANCE;
	}

