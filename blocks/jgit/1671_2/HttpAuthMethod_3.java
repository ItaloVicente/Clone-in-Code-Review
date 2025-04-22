	boolean authorize(URIish uri
		String username;
		String password;

		if (credentialsProvider != null) {
			if (credentialsProvider.supports(CredentialType.USERNAME
				username = (String) credentialsProvider.getCredentials(uri
						CredentialType.USERNAME);
				password = (String) credentialsProvider.getCredentials(uri
						CredentialType.PASSWORD);
			} else
				return false;
		} else {
			username = uri.getUser();
			password = uri.getPass();
		}
		if (username != null) {
			authorize(username
			return true;
		}
		return false;
