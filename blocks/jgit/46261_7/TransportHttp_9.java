		setURI(uri);
		http = local.getConfig().get(HttpConfig::new);
		proxySelector = ProxySelector.getDefault();
	}

	private URL toURL(URIish urish) throws MalformedURLException {
		String uriString = urish.toString();
			uriString += '/';
		}
		return new URL(uriString);
	}

	protected void setURI(final URIish uri) throws NotSupportedException {
