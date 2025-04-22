
	/** Authentication data to remember and reuse. */
	public static class CachedAuthentication {
		final String host;

		final int port;

		final String user;

		final String pass;

		/**
		 * Create a new cached authentication.
		 *
		 * @param aHost
		 *            system this is for.
		 * @param aPort
		 *            port number of the service.
		 * @param aUser
		 *            username at the service.
		 * @param aPass
		 *            password at the service.
		 */
		public CachedAuthentication(final String aHost, final int aPort,
				final String aUser, final String aPass) {
			host = aHost;
			port = aPort;
			user = aUser;
			pass = aPass;
		}

		PasswordAuthentication toPasswordAuthentication() {
			return new PasswordAuthentication(user, pass.toCharArray());
		}
	}
