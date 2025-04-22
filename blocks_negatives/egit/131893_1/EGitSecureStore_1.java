		URIish storedURI = uri.setUser(null).setPass(null).setPath(null);
		if (uri.getPort() == -1) {
			String s = uri.getScheme();
				storedURI = storedURI.setPort(80);
				storedURI = storedURI.setPort(443);
				storedURI = storedURI.setPort(22);
				storedURI = storedURI.setPort(21);
