						getSSLContext()

					@Override
					protected void prepareSocket(SSLSocket socket)
							throws IOException {
						super.prepareSocket(socket);
						HttpSupport.configureTLS(socket);
					}
				};
