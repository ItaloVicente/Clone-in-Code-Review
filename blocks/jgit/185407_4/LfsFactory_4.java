	public static CredentialsProvider getCredentialsProvider() {
		return credentialsProviderHolder.get();
	}

	public static void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		if (credentialsProvider == null) {
			removeCredentialsProvider();
		} else {
			credentialsProviderHolder.set(credentialsProvider);
		}
	}

	public static void removeCredentialsProvider() {
		credentialsProviderHolder.remove();
	}

