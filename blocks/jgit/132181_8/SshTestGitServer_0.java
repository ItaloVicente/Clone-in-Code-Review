		List<NamedFactory<Command>> subsystems = configureSubsystems();
		if (!subsystems.isEmpty()) {
			server.setSubsystemFactories(subsystems);
		}

		configureShell();
