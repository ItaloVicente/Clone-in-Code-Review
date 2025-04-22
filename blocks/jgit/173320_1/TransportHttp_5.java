	public void setPreemptiveBasicAuthentication(String username
			String password) {
		if (factoryUsed) {
			throw new IllegalStateException(JGitText.get().httpPreAuthTooLate);
		}
		if (StringUtils.isEmptyOrNull(username)
				|| StringUtils.isEmptyOrNull(password)) {
			authMethod = authFromUri(currentUri);
		} else {
			HttpAuthMethod basic = HttpAuthMethod.Type.BASIC.method(null);
			basic.authorize(username
			authMethod = basic;
		}
	}

