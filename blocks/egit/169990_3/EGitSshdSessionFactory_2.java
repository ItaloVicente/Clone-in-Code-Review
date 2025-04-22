		@Override
		protected char[] getPassword(URIish uri, String message) {
			if (store == null) {
				return super.getPassword(uri, message);
			}
			CredentialsProvider provider = getCredentialsProvider();
			if (provider == null) {
				return null;
			}
			boolean haveMessage = !StringUtils.isEmptyOrNull(message);
			List<CredentialItem> items = new ArrayList<>(haveMessage ? 3 : 2);
			if (haveMessage) {
				items.add(new CredentialItem.InformationalMessage(message));
			}
			CredentialItem.Password password = new CredentialItem.Password(
					CoreText.EGitSshdSessionFactory_sshKeyEncryptedPrompt);
			items.add(password);
			CredentialItem.YesNoType storeValue = new CredentialItem.YesNoType(
					CoreText.EGitSshdSessionFactory_sshKeyPassphraseStorePrompt);
			storeValue.setValue(useSecureStore);
			items.add(storeValue);
			try {
				if (!provider.get(uri, items)) {
					cancelAuthentication();
				}
				boolean shouldStore = storeValue.getValue();
				if (useSecureStore != shouldStore) {
					useSecureStore = shouldStore;
					savePrefs();
				}
				char[] pass = password.getValue();
				return pass == null ? null : pass.clone();
			} finally {
				password.clear();
			}
		}

