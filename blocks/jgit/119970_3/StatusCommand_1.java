		if (workingTreeIt == null) {
			try {
				workingTreeIt = new FileTreeIterator(repo);
			} catch (ConfigIllegalValueException e) {
				throw new InvalidConfigurationException(e);
			}
		}
