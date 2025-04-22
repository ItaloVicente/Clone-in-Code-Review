			String type = SOCKS_PROXY_TYPE;
			if (uri.getScheme() != null) {
				switch (uri.getScheme()) {
				case "http": //$NON-NLS-1$
					type = HTTP_PROXY_TYPE;
					break;
				case "ftp": //$NON-NLS-1$
					type = HTTP_PROXY_TYPE;
					break;
				case "https": //$NON-NLS-1$
					type = HTTPS_PROXY_TYPE;
					break;
				default:
					break;
				}
			}
