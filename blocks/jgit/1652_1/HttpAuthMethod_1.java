	boolean authorize(URIish uri
		String username = null;
		String password = null;
		if (credentialsProvider != null) {
			Credentials credentials = credentialsProvider.getCredentials(uri);
			if (credentials instanceof UsernamePasswordCredentials) {
				UsernamePasswordCredentials usernamePasswordCredentials = (UsernamePasswordCredentials) credentials;
				username = usernamePasswordCredentials.getUsername();
				password = usernamePasswordCredentials.getPassword();
		} else {
			username = uri.getUser();
			password = uri.getPass();
		}
		if (username != null) {
			authorize(username
			return true;
		}
		return false;
