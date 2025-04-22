	@Override
	public void tearDown() throws Exception {
		Set<String> existingFilters = new HashSet<>(
				FilterCommandRegistry.getRegisteredFilterCommands());
		existingFilters.forEach(FilterCommandRegistry::unregister);
		super.tearDown();
	}

