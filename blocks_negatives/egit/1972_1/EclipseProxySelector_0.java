
			final IProxyData data = service.getProxyDataForHost(host, type);
			if (data != null) {
				if (IProxyData.HTTP_PROXY_TYPE.equals(data.getType()))
					addProxy(r, Proxy.Type.HTTP, data);
				else if (IProxyData.HTTPS_PROXY_TYPE.equals(data.getType()))
					addProxy(r, Proxy.Type.HTTP, data);
				else if (IProxyData.SOCKS_PROXY_TYPE.equals(data.getType()))
					addProxy(r, Proxy.Type.SOCKS, data);
