			String username = uri.getUser();
			String host = uri.getHost();
			int port = uri.getPort();
			long t = timeout.toMillis();
			if (t <= 0) {
				session = client.connect(username, host, port).verify()
						.getSession();
			} else {
				session = client.connect(username, host, port)
						.verify(timeout.toMillis()).getSession();
			}
			session.addSessionListener(new SessionListener() {
