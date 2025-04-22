	public EGitCredentials getCredentials() {
		if ((user == null || user.length() == 0)
				&& (password == null || password.length() == 0))
			return null;
		return new EGitCredentials(user, password);
	}

