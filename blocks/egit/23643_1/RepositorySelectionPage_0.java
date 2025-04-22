				if (Protocol.HTTP.handles(finalURI)
						|| Protocol.HTTPS.handles(finalURI)) {
					UserPasswordCredentials credentials = getSecureStoreCredentials(finalURI);
					if (credentials != null) {
						String u = credentials.getUser();
						String p = credentials.getPassword();
						String uriUser = finalURI.getUser();
						if (uriUser == null) {
							setUser(u);
							setPassword(p);
							storeCheckbox.setSelection(true);
						} else if (uriUser.equals(u)) {
							setPassword(p);
							storeCheckbox.setSelection(true);
						}
					}
				}

