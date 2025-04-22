				if (Protocol.HTTP.handles(finalURI)
						|| Protocol.HTTPS.handles(finalURI)) {
					UserPasswordCredentials credentials = getSecureStoreCredentials(finalURI);
					if (credentials != null) {
						String u = credentials.getUser();
						String p = credentials.getPassword();
						String uriUser = finalURI.getUser();
						if (uriUser == null) {
							if (setSafeUser(u) || setSafePassword(p))
								storeCheckbox.setSelection(true);
						} else if (uriUser.length() != 0 && uriUser.equals(u)) {
							if (setSafePassword(p))
								storeCheckbox.setSelection(true);
						}
					}
				}

