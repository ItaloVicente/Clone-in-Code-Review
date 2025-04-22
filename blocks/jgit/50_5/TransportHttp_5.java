	private HttpURLConnection connect(final String service)
			throws TransportException
		final URL u;
		try {
			final StringBuilder b = new StringBuilder();
			b.append(baseUrl);

			if (b.charAt(b.length() - 1) != '/')
				b.append('/');
			b.append(Constants.INFO_REFS);

			b.append(b.indexOf("?") < 0 ? '?' : '&');
			b.append("service=");
			b.append(service);

			u = new URL(b.toString());
		} catch (MalformedURLException e) {
			throw new NotSupportedException("Invalid URL " + uri
		}

		try {
			final HttpURLConnection conn = httpOpen(u);
			final int status = HttpSupport.response(conn);
			switch (status) {
			case HttpURLConnection.HTTP_OK:
				return conn;

			case HttpURLConnection.HTTP_NOT_FOUND:
				throw new NoRemoteRepositoryException(uri

			case HttpURLConnection.HTTP_FORBIDDEN:
				throw new TransportException(uri

			default:
				String err = status + " " + conn.getResponseMessage();
				throw new TransportException(uri
			}
		} catch (NotSupportedException e) {
			throw e;
		} catch (TransportException e) {
			throw e;
		} catch (IOException e) {
			throw new TransportException(uri
		}
	}

	final HttpURLConnection httpOpen(final URL u) throws IOException {
		final Proxy proxy = HttpSupport.proxyFor(proxySelector
		return (HttpURLConnection) u.openConnection(proxy);
	}

	static IOException wrongContentType(String expType
		final String why = "Expected Content-Type " + expType
				+ "; received Content-Type " + actType;
		return new IOException(why);
	}

	private boolean isSmartHttp(final HttpURLConnection c
		final String expType = "application/x-" + service + "-advertisement";
		final String actType = c.getContentType();
		return expType.equals(actType);
	}

	private void readSmartHeaders(final InputStream in
			throws IOException {
		final byte[] magic = new byte[5];
		in.mark(magic.length);
		IO.readFully(in

		final int lineLen;
		try {
			lineLen = RawParseUtils.parseHexInt16(magic
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IOException("Expected pkt-line packet header");
		}
		if (magic[4] != '#')
			throw new IOException("Expected pkt-line with '# service='");
		if (lineLen <= 0 || 1000 < lineLen)
			throw new IOException("First pkt-line is too large: " + lineLen);

		in.reset();
		final PacketLineIn pckIn = new PacketLineIn(in);
		String exp = "# service=" + service;
		String act = pckIn.readString();
		if (!exp.equals(act))
			throw new IOException("Expected '" + exp + "'

		while (pckIn.readString() != PacketLineIn.END) {
		}
	}

