		Runnable terminator = new Runnable() {
			@Override
			public void run() {
				try {
					cache.clearAllExpired();
				} catch (Throwable e) {
					LOG.error(e.getMessage(), e);
				}
