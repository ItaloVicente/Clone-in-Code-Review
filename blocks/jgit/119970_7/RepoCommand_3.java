			ObjectInserter inserter;
			try {
				inserter = repo.newObjectInserter();
			} catch (ConfigIllegalValueException e) {
				throw new InvalidConfigurationException(e);
			}

