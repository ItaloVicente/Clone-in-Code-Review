			boolean pooled = true;
			SSLConnectionSocketFactory sslConnectionFactory;
			if (socketFactory != null) {
				pooled = usePooling;
				sslConnectionFactory = socketFactory;
			} else {
				pooled = (hostnameverifier == null);
				sslConnectionFactory = getSSLSocketFactory();
			}
