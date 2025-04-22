	private static final class AuthCache {
		private static final long AUTH_CACHE_EAGER_TIMEOUT = 100;

		private static final SimpleDateFormat ISO_FORMAT = new SimpleDateFormat(

		public AuthCache(Protocol.ExpiringAction action) {
			this.cachedAction = action;
			try {
				if (action.expiresIn != null && !action.expiresIn.isEmpty()) {
					this.validUntil = System.currentTimeMillis()
							+ Long.parseLong(action.expiresIn);
				} else if (action.expiresAt != null
						&& !action.expiresAt.isEmpty()) {
					this.validUntil = ISO_FORMAT.parse(action.expiresAt)
							.getTime() - AUTH_CACHE_EAGER_TIMEOUT;
				} else {
					this.validUntil = System.currentTimeMillis() + 1000;
				}
			} catch (Exception e) {
				this.validUntil = System.currentTimeMillis() + 1000;
			}
		}

		long validUntil;

		Protocol.ExpiringAction cachedAction;
	}

