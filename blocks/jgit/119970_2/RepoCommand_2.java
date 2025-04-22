			if (author == null) {
				try {
					author = new PersonIdent(repo);
				} catch (ConfigIllegalValueException e) {
					throw new InvalidConfigurationException(e);
				}
			}
