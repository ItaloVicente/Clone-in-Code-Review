				isSpecial = true;
		}

		if (!isSpecial && (userItem != null || passwordItem != null)) {
			UserPasswordCredentials credentials = getCredentialsFromSecureStore(uri);
			
			if (credentials == null) {
				credentials = getCredentialsFromUser(uri);
				if (credentials == null)
					return false;
			}
			if (userItem != null)
				userItem.setValue(credentials.getUser());
			if (passwordItem != null)
				passwordItem.setValue(credentials.getPassword().toCharArray());
			return true;
