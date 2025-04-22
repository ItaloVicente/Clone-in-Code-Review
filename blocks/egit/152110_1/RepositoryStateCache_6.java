		try (ConfigScope scope = new ConfigScope(repository)) {
			ObjectId head = ObjectId.zeroId();
			String name = null;
			Ref r = null;
			try {
				r = repository.exactRef(Constants.HEAD);
			} catch (IOException e) {
				Activator.logError(e.getLocalizedMessage(), e);
