		File configFile = getConfigFile();
		ProgressMonitor monitor = new TextProgressMonitor(out);
		SubscriptionManager manager = new SubscriptionManager(monitor

		try {
			while (true) {
				FileSnapshot configFileSnapshot = FileSnapshot.save(configFile);
				manager.sync(getConfig().getPublishers());
				while (!configFileSnapshot.isModified(configFile))
					Thread.sleep(1000 * pollTime);
			}
		} finally {
			manager.close();
		}
