			AbstractStatusHandler handler = getStatusHandler();
			if (handler != null) {
				handler.handle(statusAdapter, style);
			} else if (style != StatusManager.NONE) {
				logError(statusAdapter.getStatus());
			}
