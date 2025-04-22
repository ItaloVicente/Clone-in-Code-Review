	public static CredentialsProvider getCredentialsProvider() {
		return credentialsProviderHolder.get();
	}

	public static void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		credentialsProviderHolder.set(credentialsProvider);
	}

	public static void removeCredentialsProvider() {
		credentialsProviderHolder.remove();
	}

