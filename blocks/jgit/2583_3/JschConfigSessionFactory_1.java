				session.setUserInfo(new CredentialsProviderUserInfo(session
						credentialsProvider));
			}
			configure(hc

			if (!session.isConnected())
				session.connect(tms);

			return new JschSession(session

		} catch (JSchException je) {
			final Throwable c = je.getCause();
			if (c instanceof UnknownHostException)
				throw new TransportException(uri
			if (c instanceof ConnectException)
				throw new TransportException(uri
			throw new TransportException(uri
