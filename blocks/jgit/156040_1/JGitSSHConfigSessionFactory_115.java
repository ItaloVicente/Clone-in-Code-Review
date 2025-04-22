			@Override
			public boolean get(final URIish uri
				for (CredentialItem item : items) {
					if (item instanceof CredentialItem.YesNoType) {
						((CredentialItem.YesNoType) item).setValue(true);
					} else if (item instanceof CredentialItem.StringType) {
						((CredentialItem.StringType) item).setValue(config.getSshPassphrase());
					}
				}
				return true;
			}
		};
		final UserInfo userInfo = new CredentialsProviderUserInfo(session
		session.setUserInfo(userInfo);
		if (config.isSshOverHttpProxy() || config.isSshOverHttpsProxy()) {
			session.setProxy(buildProxy(config));
		}
	}
