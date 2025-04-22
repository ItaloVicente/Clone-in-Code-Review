		File configFile = getConfigFile();
		ProgressMonitor monitor = new TextProgressMonitor(out);
		SubscriptionManager manager = new SubscriptionManager(monitor

		try {
			while (true) {
				long changeTime = configFile.lastModified();
				manager.sync(getConfig().getPublishers());
				while (changeTime == configFile.lastModified())
			}
		} finally {
			manager.close();
		}
