
	class SmartHttpPushConnection extends BasePackPushConnection {
		private static final String REQ_TYPE = "application/x-git-receive-pack-input";

		private static final String RSP_TYPE = "application/x-git-receive-pack-status";

		SmartHttpPushConnection(final InputStream advertisement)
				throws TransportException {
			super(TransportHttp.this);

			init(advertisement
			outNeedsEnd = false;
			try {
				readAdvertisedRefs();
			} catch (IOException err) {
				close();
				throw new TransportException(uri
						"remote hung up unexpectedly"
			}
		}

		protected void doPush(final ProgressMonitor monitor
				final Map<String
				throws TransportException {
			final InputStream httpIn;
			final OutputStream httpOut;
			try {
				final URL url = new URL(baseUrl
				final Proxy proxy = HttpSupport.proxyFor(proxySelector
				final HttpURLConnection conn;

				conn = (HttpURLConnection) url.openConnection(proxy);
				conn.setRequestMethod(HttpSupport.METHOD_POST);
				conn.setInstanceFollowRedirects(false);
				conn.setDoOutput(true);
				conn.setChunkedStreamingMode(0);
				conn.setRequestProperty(HttpSupport.HDR_CONTENT_TYPE

				httpOut = new BufferedOutputStream(conn.getOutputStream());
				httpIn = new HttpInputStream(conn
			} catch (IOException e) {
				throw new TransportException(uri
			}

			init(new BufferedInputStream(httpIn)
			super.doPush(monitor
		}
	}

	static class HttpInputStream extends InputStream {
		private final HttpURLConnection conn;

		private final OutputStream httpOut;

		private final String expectedContentType;

		private InputStream httpIn;

		private HttpInputStream(HttpURLConnection conn
				String contentType) {
			this.conn = conn;
			this.httpOut = httpOut;
			this.expectedContentType = contentType;
		}

		private InputStream self() throws IOException {
			if (httpIn == null)
				endOutput();
			return httpIn;
		}

		private void endOutput() throws IOException {
			httpOut.close();

			final int status = HttpSupport.response(conn);
			if (status != HttpURLConnection.HTTP_OK)
				throw new IOException(status + " " + conn.getResponseMessage());

			httpIn = conn.getInputStream();
			final String actualContentType = conn.getContentType();
			if (!expectedContentType.equals(actualContentType))
				throw wrongContentType(expectedContentType
		}

		public int available() throws IOException {
			return self().available();
		}

		public int read() throws IOException {
			return self().read();
		}

		public int read(byte[] b
			return self().read(b
		}

		public long skip(long n) throws IOException {
			return self().skip(n);
		}

		public void close() throws IOException {
			if (httpIn != null) {
				httpIn.close();
				httpIn = null;
			}
		}
	}
