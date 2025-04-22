			try {
				URI queryUri = new URI(type, "//" + host, null); //$NON-NLS-1$
				final IProxyData[] dataArray = service.select(queryUri);
				for (IProxyData data : dataArray) {
					if (IProxyData.HTTP_PROXY_TYPE.equals(data.getType()))
						addProxy(r, Proxy.Type.HTTP, data);
					else if (IProxyData.HTTPS_PROXY_TYPE.equals(data.getType()))
						addProxy(r, Proxy.Type.HTTP, data);
					else if (IProxyData.SOCKS_PROXY_TYPE.equals(data.getType()))
						addProxy(r, Proxy.Type.SOCKS, data);
				}
			} catch (URISyntaxException e) {
