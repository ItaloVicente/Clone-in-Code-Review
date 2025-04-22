			if (repositorySelection.isSingleRepo()) {
				if (cloneSource.getStoreInSecureStore()) {
					if (!SecureStoreUtils.storeCredentials(cloneSource
							.getCredentials(), cloneSource.getSelection()
							.getURI()))
						return false;
				}
				return performClone();
			} else {
				try {

					return performMultipleClone();
				} finally {
					setWindowTitle(UIText.GitCloneWizard_title);
				}
