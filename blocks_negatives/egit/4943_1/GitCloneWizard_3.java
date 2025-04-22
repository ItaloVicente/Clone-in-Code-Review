			if (cloneSource.getStoreInSecureStore()) {
				if (!SecureStoreUtils.storeCredentials(cloneSource
						.getCredentials(), cloneSource.getSelection().getURI()))
					return false;
			}
			cloneSource.saveUriInPrefs();
			return performClone(cloneSource.getSelection().getURI(), cloneSource.getCredentials());
