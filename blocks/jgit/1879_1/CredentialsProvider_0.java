	private static volatile CredentialsProvider defaultProvider;

	public static CredentialsProvider getDefault() {
		return defaultProvider;
	}

	public static void setDefault(CredentialsProvider p) {
		defaultProvider = p;
	}

