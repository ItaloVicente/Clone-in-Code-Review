		if (allConfigurations == null) {
			allConfigurations = new FileSystemConfiguration[registeredContributions
					.size() + 1];
			allConfigurations[0] = defaultConfiguration;

			Iterator iterator = registeredContributions.iterator();
			int index = 0;
			while (iterator.hasNext()) {
				allConfigurations[++index] = (FileSystemConfiguration) iterator
						.next();
			}
		}
		return allConfigurations;
