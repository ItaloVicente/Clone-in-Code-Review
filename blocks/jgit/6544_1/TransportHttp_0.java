	@Override
	public SubscribeConnection openSubscribe()
			throws NotSupportedException
		final String service = SVC_PUBLISH_SUBSCRIBE;
		try {
			final HttpURLConnection c = connect(service);
			final InputStream in = openInputStream(c);
			try {
				if (isSmartHttp(c
					readSmartHeaders(in
					return new SmartHttpSubscribeConnection(in);
				} else {
					String msg = JGitText
							.get().remoteDoesNotSupportSmartHTTPSubscribe;
					throw new NotSupportedException(msg);
				}
			} finally {
				in.close();
			}
		} catch (IOException err) {
			throw new TransportException(uri
					.get().errorReadingInfoRefs
		}
	}

