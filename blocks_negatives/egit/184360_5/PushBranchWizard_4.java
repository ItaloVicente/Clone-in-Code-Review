			AddRemotePage remotePage = getAddRemotePage();
			if (remotePage != null) {
				storeCredentials(remotePage);
				URIish uri = remotePage.getSelection().getURI();
				configureNewRemote(uri);
			}
