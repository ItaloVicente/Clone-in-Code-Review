	private Credentials getBasicCredentials(CredentialsProvider prov
			URIish proxyURIish) {
		CredentialItem.Username u = new CredentialItem.Username();
		CredentialItem.Password p = new CredentialItem.Password();
		if (prov.supports(u
			return new UsernamePasswordCredentials(u.getValue()
					new String(p.getValue()));
		} else {
			return null;
		}
	}

	private HttpHost getProxyHost(Proxy p) {
		InetSocketAddress proxyAdr = (InetSocketAddress) p.address();
		return new HttpHost(proxyAdr.getHostName()
	}

