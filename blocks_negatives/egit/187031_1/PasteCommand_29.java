		}
		URIish finalURI;
		try {
			finalURI = new URIish(content.trim());
			if (Protocol.FILE.handles(finalURI)
					|| Protocol.GIT.handles(finalURI)
					|| Protocol.HTTP.handles(finalURI)
					|| Protocol.HTTPS.handles(finalURI)
					|| Protocol.SSH.handles(finalURI)) {
				return finalURI;
