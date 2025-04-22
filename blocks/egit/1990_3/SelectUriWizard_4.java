		uri = page.getSelection().getURI();
		if (page.getStoreInSecureStore()) {
			if (!SecureStoreUtils.storeCredentials(page.getCredentials(), uri))
				return false;
		}

