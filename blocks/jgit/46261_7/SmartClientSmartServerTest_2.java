		URIish cloneFrom = redirectURI;
		if (code != 301 || nofRedirects > 1) {
			String remoteUri = redirectURI.toString();
			int i = remoteUri.lastIndexOf('/');
			remoteUri = remoteUri.substring(0
					+ "/" + code + remoteUri.substring(i);
			cloneFrom = new URIish(remoteUri);
		}
		try (Transport t = Transport.open(dst
