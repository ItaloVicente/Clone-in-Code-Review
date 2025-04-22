		if (useExtSession()) {
			setSshSessionFactory(new SshSessionFactory() {
				@Override
				public RemoteSession getSession(URIish uri2
						CredentialsProvider credentialsProvider
						throws TransportException {
					return new ExtSession();
				}
			});
		}
