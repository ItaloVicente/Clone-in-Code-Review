	boolean authorize(URIish uri
		String username;
		String password;

		if (credentialsProvider != null) {
			CredentialItem.Username u = new CredentialItem.Username();
			CredentialItem.Password p = new CredentialItem.Password();

			if (credentialsProvider.supports(u
					&& credentialsProvider.get(uri
				username = u.getValue();
				password = new String(p.getValue());
				p.clear();
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
