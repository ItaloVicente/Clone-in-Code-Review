		} else {
			if (context != null) {
				logTracker = new ServiceTracker<>(context,
						LogService.class.getName(), null);
				logTracker.open();
				logService = logTracker.getService();
			}
