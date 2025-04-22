		URIish cloneFrom = redirectURI;
		if (code != 301 || nofRedirects > 1) {
			cloneFrom = extendPath(cloneFrom
					"/response/" + nofRedirects + "/" + code);
		}
		try (Transport t = Transport.open(dst
