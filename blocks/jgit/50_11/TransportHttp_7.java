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
			String expType = "application/x-" + service + "-advertisement";
			conn.setRequestProperty(HDR_ACCEPT
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
		HttpURLConnection conn = (HttpURLConnection) u.openConnection(proxy);
		conn.setRequestProperty(HDR_ACCEPT_ENCODING
		conn.setRequestProperty(HDR_PRAGMA
		conn.setRequestProperty(HDR_USER_AGENT
		return conn;
	}

	final InputStream openInputStream(HttpURLConnection conn)
			throws IOException {
		InputStream input = conn.getInputStream();
		if (ENCODING_GZIP.equals(conn.getHeaderField(HDR_CONTENT_ENCODING)))
			input = new GZIPInputStream(input);
		return input;
	}

	IOException wrongContentType(String expType
		final String why = "expected Content-Type " + expType
				+ "; received Content-Type " + actType;
		return new TransportException(uri
	}

	private boolean isSmartHttp(final HttpURLConnection c
		final String expType = "application/x-" + service + "-advertisement";
		final String actType = c.getContentType();
		return expType.equals(actType);
	}

	private void readSmartHeaders(final InputStream in
			throws IOException {
		final byte[] magic = new byte[5];
		IO.readFully(in
		if (magic[4] != '#') {
			throw new TransportException(uri
					+ " '# service='
					+ "'");
		}

		final PacketLineIn pckIn = new PacketLineIn(new UnionInputStream(
				new ByteArrayInputStream(magic)
		final String exp = "# service=" + service;
		final String act = pckIn.readString();
		if (!exp.equals(act)) {
			throw new TransportException(uri
					+ act + "'");
		}

		while (pckIn.readString() != PacketLineIn.END) {
		}
	}

