	private HttpURLConnection connect(final String service)
			throws TransportException
		final URL u;
		try {
			final String args = "service=" + service;
			u = new URL(baseUrl
		} catch (MalformedURLException e) {
			throw new NotSupportedException("Invalid URL " + uri
		}

		try {
			final Proxy proxy = HttpSupport.proxyFor(proxySelector
			final HttpURLConnection c;

			c = (HttpURLConnection) u.openConnection(proxy);
			switch (HttpSupport.response(c)) {
			case HttpURLConnection.HTTP_OK:
				return c;

			case HttpURLConnection.HTTP_NOT_FOUND:
				throw new NoRemoteRepositoryException(uri

			case HttpURLConnection.HTTP_FORBIDDEN:
				throw new TransportException(uri

			default:
				throw new TransportException(uri
						+ c.getResponseMessage());
			}
		} catch (NotSupportedException e) {
			throw e;
		} catch (TransportException e) {
			throw e;
		} catch (IOException e) {
			throw new TransportException(uri
		}
	}

	static IOException wrongContentType(String expType
		final String why = "Expected Content-Type " + expType
				+ "; received Content-Type " + actType;
		return new IOException(why);
	}

	private void checkPacketLineStream(final InputStream in
			final String service) throws IOException {
		final byte[] test = new byte[5];
		in.mark(test.length);
		NB.readFully(in

		final int lineLen;
		try {
			lineLen = RawParseUtils.parseHexInt16(test
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IOException("Expected pkt-line packet header");
		}
		if (test[4] != '#')
			throw new IOException("Expected pkt-line with '# service='");
		if (lineLen <= 0 || 1000 < lineLen)
			throw new IOException("First pkt-line is too large");

		in.reset();
		String exp = "# service=" + service;
		String act = new PacketLineIn(in).readString().trim();
		if (!exp.equals(act))
			throw new IOException("Expected '" + exp + "'
	}

