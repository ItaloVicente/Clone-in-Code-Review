		if (gitSession == null && (factory instanceof HttpConnectionFactory2)) {
			gitSession = ((HttpConnectionFactory2) factory).newSession();
		}
		if (gitSession != null) {
			try {
				gitSession.configure(conn
			} catch (GeneralSecurityException e) {
				throw new IOException(e.getMessage()
			}
