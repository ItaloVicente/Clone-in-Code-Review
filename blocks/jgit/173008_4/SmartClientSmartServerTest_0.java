	@Test
	public void test_CloneWithCustomFactory() throws Exception {
		HttpConnectionFactory globalFactory = HttpTransport
				.getConnectionFactory();
		HttpConnectionFactory failingConnectionFactory = new HttpConnectionFactory() {

			@Override
			public HttpConnection create(URL url) throws IOException {
				throw new IOException("Should not be reached");
			}

			@Override
			public HttpConnection create(URL url
					throws IOException {
				throw new IOException("Should not be reached");
			}
		};
		HttpTransport.setConnectionFactory(failingConnectionFactory);
		try {
			File tmp = createTempDirectory("cloneViaApi");
			boolean[] localFactoryUsed = { false };
			TransportConfigCallback callback = new TransportConfigCallback() {

				@Override
				public void configure(Transport transport) {
					if (transport instanceof TransportHttp) {
						((TransportHttp) transport).setHttpConnectionFactory(
								new HttpConnectionFactory() {

									@Override
									public HttpConnection create(URL url)
											throws IOException {
										localFactoryUsed[0] = true;
										return globalFactory.create(url);
									}

									@Override
									public HttpConnection create(URL url
											Proxy proxy) throws IOException {
										localFactoryUsed[0] = true;
										return globalFactory.create(url
									}
								});
					}
				}
			};
			try (Git git = Git.cloneRepository().setDirectory(tmp)
					.setTransportConfigCallback(callback)
					.setURI(remoteURI.toPrivateString()).call()) {
				assertTrue("Should have used the local HttpConnectionFactory"
						localFactoryUsed[0]);
			}
		} finally {
			HttpTransport.setConnectionFactory(globalFactory);
		}
	}

