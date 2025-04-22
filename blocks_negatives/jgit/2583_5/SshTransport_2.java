		final String user = uri.getUser();
		final String pass = uri.getPass();
		final String host = uri.getHost();
		final int port = uri.getPort();
		try {
			sock = sch.getSession(user, pass, host, port,
					getCredentialsProvider(), local.getFS());
			if (!sock.isConnected())
				sock.connect(tms);
		} catch (JSchException je) {
			final Throwable c = je.getCause();
			if (c instanceof UnknownHostException)
				throw new TransportException(uri, JGitText.get().unknownHost);
			if (c instanceof ConnectException)
				throw new TransportException(uri, c.getMessage());
			throw new TransportException(uri, je.getMessage(), je);
		}
