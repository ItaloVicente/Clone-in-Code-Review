			if (null != uri.getScheme())
				switch (uri.getScheme()) {
				case "http": //$NON-NLS-1$
			    type = IProxyData.HTTP_PROXY_TYPE;
			    break;
				case "ftp": //$NON-NLS-1$
			    type = IProxyData.HTTP_PROXY_TYPE;
			    break;
				case "https": //$NON-NLS-1$
			    type = IProxyData.HTTPS_PROXY_TYPE;
			    break;
		    	default:
			    break;
		    }
