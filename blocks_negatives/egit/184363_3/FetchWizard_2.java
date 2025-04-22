		if (repoPage.getStoreInSecureStore()) {
			if (!SecureStoreUtils.storeCredentials(repoPage.getCredentials(),
					repoPage.getSelection().getURI()))
				return false;
		}

