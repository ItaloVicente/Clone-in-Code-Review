			if (cloneSource.getStoreInSecureStore()) {
				if (!SecureStoreUtils.storeCredentials(cloneSource
						.getCredentials(), cloneSource.getSelection().getURI()))
					return false;
			}
			return performClone();
