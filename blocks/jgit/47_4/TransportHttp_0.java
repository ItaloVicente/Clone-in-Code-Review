		try {
			final String service = "git-receive-pack";
			final HttpURLConnection c = connect(service);
			final InputStream in = new BufferedInputStream(c.getInputStream());
			try {
				String expType = "application/x-" + service + "-advertisement";
				String actType = c.getContentType();
				if (expType.equals(actType)) {
					checkPacketLineStream(in
					return new SmartHttpPushConnection(in);

				} else {
					String msg = "Smart HTTP transport not supported";
					IOException why = wrongContentType(expType
					throw new NotSupportedException(msg
				}
			} finally {
				in.close();
			}
		} catch (IOException err) {
			throw new TransportException(uri
		}
