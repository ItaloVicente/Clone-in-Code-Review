		if (MANAGER != null) {
			return MANAGER;
		}
		synchronized (StatusManager.class) {
			if (MANAGER == null) {
				MANAGER = new StatusManager();
			}
