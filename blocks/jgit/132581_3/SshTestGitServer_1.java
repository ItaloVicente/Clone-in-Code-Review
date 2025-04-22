	public void enablePasswordAuthentication() {
		server.setPasswordAuthenticator((user
			return testUser.equals(user)
					&& testUser.toUpperCase(Locale.ROOT).equals(pwd);
		});
	}

	public void enableKeyboardInteractiveAuthentication() {
		server.setPasswordAuthenticator((user
			return testUser.equals(user)
					&& testUser.toUpperCase(Locale.ROOT).equals(pwd);
		});
		server.setKeyboardInteractiveAuthenticator(
				DefaultKeyboardInteractiveAuthenticator.INSTANCE);
	}

