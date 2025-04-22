	private static final class AuthCache {
		private static final long AUTH_CACHE_EAGER_TIMEOUT = 100;

		private static final SimpleDateFormat ISO_FORMAT = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSX");

		public AuthCache(Protocol.ExpiringAction action) {
			this.cachedAction = action;
			try {
				this.validUntil = ISO_FORMAT.parse(action.expiresAt).getTime()
						- AUTH_CACHE_EAGER_TIMEOUT;
			} catch (Exception e) {
				this.validUntil = System.currentTimeMillis() + 1000;
			}
		}

		long validUntil;

		Protocol.ExpiringAction cachedAction;
	}

