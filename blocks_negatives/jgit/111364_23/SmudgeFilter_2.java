				URL contentUrl = new URL(downloadAction.href);
				HttpConnection contentServerConn = HttpTransport
						.getConnectionFactory().create(contentUrl,
								HttpSupport.proxyFor(ProxySelector.getDefault(),
										contentUrl));
				contentServerConn.setRequestMethod(HttpSupport.METHOD_GET);
				downloadAction.header.forEach(
						(k, v) -> contentServerConn.setRequestProperty(k, v));
						.getBoolean(HttpConfig.HTTP, HttpConfig.SSL_VERIFY_KEY,
								true)) {
					HttpSupport.disableSslVerify(contentServerConn);
				}
				contentServerConn.setRequestProperty(HDR_ACCEPT_ENCODING,
						ENCODING_GZIP);
