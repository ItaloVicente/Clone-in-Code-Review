		final String service = SVC_RECEIVE_PACK;
		try {
			final HttpURLConnection c = connect(service);
			final InputStream in = c.getInputStream();
			try {
				if (isSmartHttp(c
					readSmartHeaders(in
					return new SmartHttpPushConnection(in);

				} else {
					final String msg = "remote does not support smart HTTP push";
					throw new NotSupportedException(msg);
				}
			} finally {
				in.close();
			}
		} catch (IOException err) {
			throw new TransportException(uri
		}
