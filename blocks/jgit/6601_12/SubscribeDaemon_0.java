		File configFile = getConfigFile();
		SubscriptionExecutor manager = new SubscriptionExecutor(out);

		try {
			while (true) {
				FileSnapshot configFileSnapshot = FileSnapshot.save(configFile);
				manager.applyConfig(getConfig().getPublishers());
				while (!configFileSnapshot.isModified(configFile))
					Thread.sleep(1000 * pollTime);
			}
		} finally {
			manager.close();
		}
