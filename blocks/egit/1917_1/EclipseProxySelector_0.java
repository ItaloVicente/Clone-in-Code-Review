		if (host != null) {
			String type = IProxyData.SOCKS_PROXY_TYPE;
			if ("http".equals(uri.getScheme())) //$NON-NLS-1$
				type = IProxyData.HTTP_PROXY_TYPE;
			else if ("ftp".equals(uri.getScheme())) //$NON-NLS-1$
				type = IProxyData.HTTP_PROXY_TYPE;
			else if ("https".equals(uri.getScheme())) //$NON-NLS-1$
				type = IProxyData.HTTPS_PROXY_TYPE;
