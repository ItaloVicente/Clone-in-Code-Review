	private boolean logged = false;

	private ISafeRunnable updateRunner;

	private ISafeRunnable getUpdateRunner() {
		if (updateRunner == null) {
			updateRunner = new ISafeRunnable() {
				@Override
				public void run() throws Exception {
					boolean shouldEnable = canExecuteItem(null);
					if (shouldEnable != model.isEnabled()) {
						model.setEnabled(shouldEnable);
						update();
					}
				}

				@Override
				public void handleException(Throwable exception) {
					if (!logged) {
						logged = true;
						if (logger != null) {
							logger.error(exception,
									"Internal error during tool item enablement updating, this is only logged once per tool item."); //$NON-NLS-1$
						}
					}
				}
			};
		}
		return updateRunner;
	}

