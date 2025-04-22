
	class SmartHttpFetchConnection extends BasePackFetchConnection {
		SmartHttpFetchConnection(final InputStream advertisement)
				throws TransportException {
			super(TransportHttp.this);
			statelessRPC = true;

			init(advertisement
			outNeedsEnd = false;
			try {
				readAdvertisedRefs();
			} catch (IOException err) {
				close();
				throw new TransportException(uri
			}
		}

		@Override
		protected void doFetch(final ProgressMonitor monitor
				final Collection<Ref> want
				throws TransportException {
			final Service svc = new Service(SVC_UPLOAD_PACK);
			init(svc.in
			super.doFetch(monitor
		}
	}

	class SmartHttpPushConnection extends BasePackPushConnection {
		SmartHttpPushConnection(final InputStream advertisement)
				throws TransportException {
			super(TransportHttp.this);
			statelessRPC = true;

			init(advertisement
			outNeedsEnd = false;
			try {
				readAdvertisedRefs();
			} catch (IOException err) {
				close();
				throw new TransportException(uri
			}
		}

		protected void doPush(final ProgressMonitor monitor
				final Map<String
				throws TransportException {
			final Service svc = new Service(SVC_RECEIVE_PACK);
			init(svc.in
			super.doPush(monitor
		}
	}

	class Service {
		private final String serviceName;

		private final String requestType;

		private final String responseType;

		private final UnionInputStream httpIn;

		final HttpInputStream in;

		final HttpOutputStream out;

		HttpURLConnection conn;

		Service(final String serviceName) {
			this.serviceName = serviceName;
			this.requestType = "application/x-" + serviceName + "-request";
			this.responseType = "application/x-" + serviceName + "-result";

			this.httpIn = new UnionInputStream();
			this.in = new HttpInputStream(httpIn);
			this.out = new HttpOutputStream();
		}

		void openStream() throws IOException {
			conn = httpOpen(new URL(baseUrl
			conn.setRequestMethod(HttpSupport.METHOD_POST);
			conn.setInstanceFollowRedirects(false);
			conn.setDoOutput(true);
			conn.setRequestProperty(HttpSupport.HDR_CONTENT_TYPE
		}

		void execute() throws IOException {
			out.close();

			if (conn == null) {
				if (out.length() == 0) {
					throw new IOException("Starting read stage"
							+ " without written request data pending"
							+ " is not supported");
				}
				openStream();
				conn.setFixedLengthStreamingMode((int) out.length());
				final OutputStream httpOut = conn.getOutputStream();
				try {
					out.writeTo(httpOut
				} finally {
					httpOut.close();
				}
			}

			out.reset();

			final int status = HttpSupport.response(conn);
			if (status != HttpURLConnection.HTTP_OK)
				throw new IOException(status + " " + conn.getResponseMessage());

			httpIn.add(conn.getInputStream());

			final String contentType = conn.getContentType();
			if (!responseType.equals(contentType)) {
				httpIn.close();
				throw wrongContentType(responseType
			}

			conn = null;
		}

		class HttpOutputStream extends TemporaryBuffer {
			HttpOutputStream() {
				super(http.postBuffer);
			}

			@Override
			protected OutputStream overflow() throws IOException {
				openStream();
				conn.setChunkedStreamingMode(0);
				return conn.getOutputStream();
			}
		}

		class HttpInputStream extends InputStream {
			private final UnionInputStream src;

			HttpInputStream(UnionInputStream httpIn) {
				this.src = httpIn;
			}

			private InputStream self() throws IOException {
				if (src.isEmpty()) {
					execute();
				}
				return src;
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
				src.close();
			}
		}
	}
